README!!

Implementasi aplikasi Link Aja di laptop
1. Install Java SDK dengan cara mendownload .jar di website resmi : https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
2. Install Intellij sebagai editor IDE Java, untuk mendapatkan Free pilih Community edition. Silahkan akses web berikut : https://www.jetbrains.com/idea/download/#section=windows
3. Install MySql WorkBranch sebagai databasenya. MySql WorkBranch dapat diunduh disini : https://dev.mysql.com/downloads/windows/installer/8.0.html
4. Mysql Workbranch memerlukan 2 aplikasi yang perlu kita install secara manual, yaitu Visual Code dan phyton versi 3.x
4. Untuk testing api yang akan kita buat, akan lebih mudah menggunakan postman yang dapat diunduh disini : https://www.postman.com/downloads/
5. Selanjutnya menyiapkan Docker dengan mendownloadnya disini : https://www.docker.com/products/docker-desktop
6. Untuk menginstall docker juga diperlukan aplikasi external, panduannya dapat di akses di : https://docs.docker.com/docker-for-windows/install/
7. Intellij dapat langsung digunakan untuk develop App menggunakan frame work SpringBoot
8. Mysql perlu membuat connection terlebih dahulu kemudian create database agar kemudian dapat di hubungkan dengan Intellij
9. Table nantinya dibuat agar value account_number dan customer_number tidak boleh ada yang sama
10. Setting MySql dispringBoot dapat set di application.properties
11. Extract zip dari springboot project
12. Arahkan command prompt pada directory springboot project tadi
13. Ketikkan "docker-compose up mysqldb" (tanpa petik), tunggu hingga proses berhasil
14. Buka window command yang baru lagi dengan directory yang sama
15. Ketikkan "docker-compose up web-service" (tanpa petik), tunggu hingga aplikasi springboot berjalan
16. API sudah dapat digunakan untuk testing dengan spect API :
## Menambahkan data customer :
curl --location --request POST 'localhost:8080/account/addCustomer/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "account_number": 555012,
    "customer_number": 1006,
    "balance": 20000,
    "name": "Kurniawan"
}'

## Get balance
curl --location --request GET 'localhost:8080/account/{account_number}'

## Transfer Balance
curl --location --request POST 'localhost:8080/account/{from_account_number}/transfer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "to_account_number":{to_account_number},
    "amount":1000
}'




Happy Coding!!!

Best Regards
Yosua Ardi Kurniawan