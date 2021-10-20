FROM alpine as actions
ADD ./actions/Bundle-API.tar /src/api/

FROM openjdk:17
WORKDIR /src/api
COPY --from=actions /src/api/Bundle-API-* /src/api
ENTRYPOINT [ "sh", "/src/api/bin/Bundle-API" ]