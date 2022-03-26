
docker run -e "MYSQL_ROOT_PASSWORD=Secret_123" --name sahabtmysql1 --rm -p 3306:3306 -d sahabt-mysql --lower_case_table_names=1
docker run -e "SPRING_DATASOURCE_URL=jdbc:mysql://sahabtmysql1:3306/world?allowPublicKeyRetrieval=true&useSSL=false" --link sahabtmysql1:sahabtmysql1 --name sahabtworld1 --rm -p 8100:8100 -d sahabt-world
docker run --name sahabtworldfrontend1 --rm -p 8080:80 -d sahabt-world-frontend

