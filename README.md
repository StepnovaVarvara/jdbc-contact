# Contact Register (JDBC)

## Приложение учет контактов
__Принципы работы:__  
Интерфейс приложения реализован с помощью Thymeleaf.
Пользователь может создать, просмотреть, удалить, сохранить и обновить данные о контактах через UI.

## Стек используемых технологий:

* Java
* Spring Core
* Spring MVC
* Thymeleaf
* JdbcTemplate
* Docker
* Lombok

## Инструкция по локальному запуску проекта
__Подготовка к работе:__  
В директории docker расположен файл docker-compose.yml, в котором определены настройки по созданию контейнера  и подключению к БД postgreSQL, init.sql - скрипт для инициализации БД.
В application.yml для spring указаны свойства для подключения к БД.

__Запуск проекта:__ 
Для запуска docker-compose файла открываем терминал, командой cd docker переходим в папку docker, командой docker-compose up запускаем файл локально.

Запускаем проект и открываем его через браузер по адресу:
http://localhost:8080/

На начальной странице отобразиться список контактов, сохраненных в БД (при наличии).

__Команды API:__
> Создание контакта - кнопка "Create contact". GET-запрос "__/contact/create__" и POST-запрос "__/contact/create__"
 
GET-запрос "/contact/create", возвращает страницу create.html, в которой с помощью Thymeleaf реализован интерфейс с полями сущности контакта для его создания. 
После заполнения полей контакта и нажатия кнопки "Save" выполняется POST-запрос "/contact/create", который сохраняет контакт в БД и возвращает страницу index.html со списком имеющихся контактов.

> Редактирование контакта - кнопка "Edit". Get-запрос "__/contact/edit/{id}__" и POST-запрос "__/contact/edit__"

Get-запрос "/contact/edit/{id}", возвращает страницу edit.html, в которой с помощью Thymeleaf реализован интерфейс с полями сущности выбранного контакта для их редактирования.
После редактирования полей контакта и нажатия кнопки "Save" выполняется POST-запрос "/contact/edit", который сохраняет изменения и возвращает страницу index.html со списком имеющихся контактов с учетом внесенных изменений.

> Удаление контакта - кнопка "Delete". Get-запрос "__contact/delete/{id}__"  

Get-запрос "/contact/delete/{id}", удаяет выбранный контакт и возвращает страницу index.html с обновленным списком контактов.