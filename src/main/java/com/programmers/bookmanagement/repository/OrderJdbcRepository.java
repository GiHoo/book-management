package com.programmers.bookmanagement.repository;

import com.programmers.bookmanagement.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.bookmanagement.common.Utils.toLocalDateTime;
import static com.programmers.bookmanagement.common.Utils.toUUID;

@Repository
@RequiredArgsConstructor
public class OrderJdbcRepository implements OrderRepository {

    private static final String INSERT_ORDER = """
            INSERT
            INTO orders(order_id, name, email, address, postcode, order_status, created_at, updated_at)
            VALUES(UUID_TO_BIN(:orderId), :name, :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)
            """;

    private static final String INSERT_ITEM = """
            INSERT
            INTO order_items(order_id, book_id, category, price, quantity, created_at, updated_at)
            VALUES(UUID_TO_BIN(:orderId), UUID_TO_BIN(:bookId), :category, :price, :quantity, :createdAt, :updatedAt)
            """;

    private static final String FIND_BY_ID = """
            SELECT *        
            FROM orders
            WHERE order_id = UUID_TO_BIN(:orderId)
            """;

    private static final String FIND_ALL = """
            SELECT *
            FROM orders
            """;

    private static final String DELETE_BY_ID = """
            DELETE
            FROM orders
            WHERE order_id = UUID_TO_BIN(:orderId)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    @Transactional
    public Order insert(Order order) {
        jdbcTemplate.update(INSERT_ORDER, toOrderParamMap(order));

        order.getOrderItems()
                .forEach(orderItem -> jdbcTemplate.update(INSERT_ITEM, toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), orderItem)));

        return order;
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(FIND_ALL, new OrderRowMapper());
    }

    @Override
    public Order delete(UUID orderId) {
        Order findOrder = findById(orderId).get();
        jdbcTemplate.update(DELETE_BY_ID, Collections.singletonMap("orderId", orderId.toString().getBytes()));
        return findOrder;
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(FIND_BY_ID,
                            Collections.singletonMap("orderId", orderId.toString().getBytes()),
                            new OrderRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UUID orderId = toUUID(resultSet.getBytes("order_id"));
            String name = resultSet.getString("name");
            Email email = new Email(resultSet.getString("email"));
            String address = resultSet.getString("address");
            String postcode = resultSet.getString("postcode");
            OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
            LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
            LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

            List<OrderItem> orderItems = findOrderItemsByOrderId(orderId);

            return new Order(orderId, name, email, address, postcode, orderItems, orderStatus, createdAt, updatedAt);
        }
    }

    private List<OrderItem> findOrderItemsByOrderId(UUID orderId) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("orderId", orderId.toString().getBytes());

        return jdbcTemplate.query(
                "SELECT * FROM order_items WHERE order_id = UUID_TO_BIN(:orderId)",
                paramMap,
                new OrderItemRowMapper()
        );
    }

    private class OrderItemRowMapper implements RowMapper<OrderItem> {
        @Override
        public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            UUID bookId = toUUID(rs.getBytes("book_id"));
            Category category = Category.valueOf(rs.getString("category"));
            long price = rs.getLong("price");
            int quantity = rs.getInt("quantity");
            return new OrderItem(bookId, category, price, quantity);
        }
    }





    //삽입 시 사용

    private static Map<String, Object> toOrderParamMap(Order order) {
        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("orderId", order.getOrderId().toString().getBytes());
        paramMap.put("name", order.getName());
        paramMap.put("email", order.getEmail().getAddress());
        paramMap.put("address", order.getAddress());
        paramMap.put("postcode", order.getPostcode());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createdAt", order.getCreatedAt());
        paramMap.put("updatedAt", order.getUpdatedAt());

        return paramMap;
    }

    private static Map<String, Object> toOrderItemParamMap(UUID orderId, LocalDateTime createdAt, LocalDateTime updatedAt, OrderItem orderItem) {
        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("orderId", orderId.toString().getBytes());
        paramMap.put("bookId", orderItem.bookId().toString().getBytes());
        paramMap.put("category", orderItem.category().toString());
        paramMap.put("price", orderItem.price());
        paramMap.put("quantity", orderItem.quantity());
        paramMap.put("createdAt", createdAt);
        paramMap.put("updatedAt", updatedAt);

        return paramMap;
    }
}
