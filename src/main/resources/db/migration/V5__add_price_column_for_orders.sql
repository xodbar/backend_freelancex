alter table t_orders
    add column price int8 default null;

alter table t_orders
    add constraint check_min_price_for_price check ( t_orders.price >= 500 );
