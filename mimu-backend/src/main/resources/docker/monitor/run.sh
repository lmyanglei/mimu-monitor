sudo docker run \
-d \
--name=monitor \
-e "SPRING_PROFILES_ACTIVE=sthqinner" \
--restart=always
-p 9877:9877 \
monitor:1.0.2
