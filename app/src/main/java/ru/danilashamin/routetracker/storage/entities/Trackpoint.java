package ru.danilashamin.routetracker.storage.entities;

import androidx.core.util.ObjectsCompat;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.elogroup.tracker.logic.entities.order.OrderStatus;
import com.elogroup.tracker.network.models.request.PendingTrackpoint;
import com.elogroup.tracker.storage.config.AppDbConfig;
import com.elogroup.tracker.storage.converters.DateTimeTypeConverter;
import com.elogroup.tracker.storage.converters.OrderStatusTypeConverter;

import org.threeten.bp.LocalDateTime;


@Entity(tableName = AppDbConfig.TRACKPOINT.TABLE_NAME, foreignKeys = {
        @ForeignKey(entity = Order.class, parentColumns = AppDbConfig.ORDER.ID, childColumns = AppDbConfig.TRACKPOINT.ORDER_ID, onDelete = ForeignKey.CASCADE)
})
public final class Trackpoint {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AppDbConfig.TRACKPOINT.ID)
    private final long id;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.LATITUDE)
    private final double latitude;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.LONGITUDE)
    private final double longitude;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.ADDRESS)
    private final String address;

    @TypeConverters(value = DateTimeTypeConverter.class)
    @ColumnInfo(name = AppDbConfig.TRACKPOINT.CREATED_AT)
    private final LocalDateTime createdAt;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.ORDER_ID)
    private final long orderId;

    @ColumnInfo(name = AppDbConfig.TRACKPOINT.ORDER_SERVER_ID)
    private final String orderServerId;

    @TypeConverters(OrderStatusTypeConverter.class)
    @ColumnInfo(name = AppDbConfig.TRACKPOINT.STATUS)
    private final OrderStatus orderStatus;

    public Trackpoint(long id, double latitude, double longitude, String address, LocalDateTime createdAt, long orderId, String orderServerId, OrderStatus orderStatus) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.createdAt = createdAt;
        this.orderId = orderId;
        this.orderServerId = orderServerId;
        this.orderStatus = orderStatus;
    }

    public Trackpoint(Builder builder) {
        id = builder.id;
        latitude = builder.latitude;
        longitude = builder.longitude;
        createdAt = builder.createdAt;
        orderId = builder.orderId;
        orderServerId = builder.orderServerId;
        address = builder.address;
        orderStatus = builder.orderStatus;
    }

    public long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getOrderServerId() {
        return orderServerId;
    }

    public String getAddress() {
        return address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public static Trackpoint from(PendingTrackpoint trackpoint, Order order) {
        return new Builder()
                .setCreatedAt(trackpoint.getCreatedAtTime())
                .setLatitude(trackpoint.getLatitude())
                .setLongitude(trackpoint.getLongitude())
                .setAddress(trackpoint.getAddress())
                .setOrderId(order.getId())
                .setOrderServerId(order.getServerId())
                .setOrderStatus(order.getStatus())
                .build();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trackpoint)) return false;
        Trackpoint that = (Trackpoint) o;
        return id == that.id &&
                Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                orderId == that.orderId &&
                ObjectsCompat.equals(address, that.address) &&
                ObjectsCompat.equals(createdAt, that.createdAt) &&
                ObjectsCompat.equals(orderServerId, that.orderServerId) &&
                orderStatus == that.orderStatus;
    }

    @Override
    public int hashCode() {
        return ObjectsCompat.hash(id, latitude, longitude, address, createdAt, orderId, orderServerId, orderStatus);
    }

    public static final class Builder extends BaseBuilder<Trackpoint, Builder> {
        private double latitude;
        private double longitude;
        private LocalDateTime createdAt;
        private long orderId;
        private String orderServerId;
        private String address;
        private OrderStatus orderStatus;

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setOrderId(long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setOrderServerId(String orderServerId) {
            this.orderServerId = orderServerId;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        @Override
        Builder self() {
            return this;
        }

        public Trackpoint build() {
            return new Trackpoint(this);
        }
    }
}
