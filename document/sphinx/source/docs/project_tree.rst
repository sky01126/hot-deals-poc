.. role:: raw-html-m2r(raw)
   :format: html


프로젝트 구조
=============

Parent 프로젝트 구조 설명
^^^^^^^^^^^^^^^^^^^^^^^^^

.. list-table::
   :header-rows: 1

   * - **구조**
     - **설명**
   * - eclipse
     - 이클립스에서 사용될 codetemplates.xml, eclipse-code-formatter.xml, templates.xml 파일
   * - mvnw / mvnw.cmd
     - Maven Wrapper(이하 "mvnw")는 Apache Maven을 프로젝트에서 요구하는 버전으로 유지하기 위해 사용하는 유용한 도구이다..\ :raw-html-m2r:`<br />`\ 로컬 시스템에 Maven이 설치되어 있지 않거나 혹은, Maven 버전이 2.x대의 낮은 버전의 경우 사용한다.
   * - sample-commons
     - 프로젝트에서 공통으로 사용될 라이브러리
   * - sample-example
     - 프로젝트의 Service / Dao 샘플 소스
   * - sample-webapp
     - WEB / RESTFul 프로젝트, 프로젝트 추가시 복사해서 사용.


Parent 프로젝트 POM 파일 설명
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Commons 프로젝트 구조 설명
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. list-table::
   :header-rows: 1

   * - **구조**
     - **설명**
   * - com.sample.commons
     - AbstractDataSourceConfiguration / AbstractWebMvcConfiguration
   * - com.sample.commons.config
     - 프로젝트의 Java에서 공통으로 사용될 Config 클래스 제공.
   * - com.sample.commons.dto.request
     - 프로젝트 공통 Request DTO(Data Transfer Object) 클래스 제공.
   * - com.sample.commons.dto.response
     - 프로젝트 공통 Response DTO(Data Transfer Object) 클래스 제공.
   * - com.sample.commons.enums.type
     - 프로젝트 공통 열거형(enum) Type 클래스 정의.
   * - com.sample.commons.persistence.dao
     - 프로젝트 공통 DAO(Data Access Object) 클래스 제공.
   * - com.sample.commons.persistence.model
     - 프로젝트 공통 Persistence Model 클래스 제공.
   * - com.sample.commons.service
     - 프로젝트 공통 Service 클래스 제공.
   * - com.sample.commons.web.controller
     - 프로젝트 공통 Controller 클래스 제공.
   * - com.sample.commons.web.filter
     - 프로젝트 공통 Servlet Filter 클래스 제공.
   * - com.sample.commons.web.handler
     - 프로젝트 공통 Exception Handler 클래스 제공.
   * - com.sample.commons.web.util
     - 프로젝트 공통 Web Utility 클래스 제공.


Example 프로젝트 구조 설명
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. list-table::
   :header-rows: 1

   * - **구조**
     - **설명**
   * - com.sample.dto.request
     - Example Request DTO(Data Transfer Object) 클래스 제공.
   * - com.sample.dto.response
     - Example Response DTO(Data Transfer Object) 클래스 제공.
   * - com.sample.persistence.dao
     - Example DAO(Data Access Object) 클래스 제공.
   * - com.sample.persistence.model
     - Example Persistence Model 클래스 제공.
   * - com.sample.service
     - Example Service 클래스 제공.
   * - com.sample.web.controller
     - Example Controller 클래스 제공.


WEB / RESTFul 프로젝트 구조 설명
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. list-table::
   :header-rows: 1

   * - **구조**
     - **설명**
   * - com.sample
     - Spring boot 설정 (Configuration) 클래스 제공.
   * - com.sample.dto.request
     - 프로젝트의 Request DTO(Data Transfer Object) 클래스 제공.\ :raw-html-m2r:`<br />`\ AbstractRequest를 상속 받아서 사용하는 것을 추천한다.
   * - com.sample.dto.response
     - 프로젝트의 Response DTO(Data Transfer Object) 클래스 제공.\ :raw-html-m2r:`<br />`\ ValidationResponse를 상속 받아서 사용하는 것을 추천한다.
   * - com.sample.persistence.dao
     - 프로젝트의 DAO(Data Access Object) 클래스 제공.\ :raw-html-m2r:`<br />`\ AbstractMybatisDao에 DataSource에 관한 설정과 select / insert / update Method가 있다.
   * - com.sample.persistence.model
     - 프로젝트의 Persistence Model 클래스 제공.\ :raw-html-m2r:`<br />`\ AbstractModel을 상속 받아서 사용하는 것을 추천한다.
   * - com.sample.scheduler
     - 프로젝트의 Scheduler 클래스 제공.
   * - com.sample.service
     - 프로젝트의 Service 클래스 제공.\ :raw-html-m2r:`<br />`\ AbstractService를 상속 받아서 사용하는 것을 추천한다.
   * - com.sample.web.controller
     - 공통 MVC Controller 클래스 제공.\ :raw-html-m2r:`<br />`\ AbstractController를 상속 받아서 사용하는 것을 추천한다.
   * - com.sample.web.handler.
     - 프로젝트의 Handler 클래스 제공.\ :raw-html-m2r:`<br />`\ 기본으로 GlobalControllerExceptionHandler가 있다.


Parent POM 파일 설명
^^^^^^^^^^^^^^^^^^^^

..

   Parent POM 파일의 마지막에 Maven Multi-Module Project 설정이 있음..\ :raw-html-m2r:`<br />`
   프로젝트가 추가되면 module에 등록한 후 import하면 프로젝트로 등록된다.


.. code-block:: xml

       <modules>
           <module>sample-commons</module>
           <module>sample-example</module>
           <module>sample-webapp</module>
       </modules>

.. list-table::
   :header-rows: 1

   * - **POM 엘리먼트**
     - **설명**
   * - name / description
     - name과 description 정보는 프로젝트에 맞게 변경한다. (하위 프로젝트도 동일하게 수정한다.)
   * - parent
     - 상위 POM 프로젝트 선언, sample 프로젝트에서는 spring-boot 1.5.3.RELEASE을 사용한다.
   * - scm
     - 형상관리 URL 설정.
   * - dependencyManagement
     - Dependency의 버전 관리
   * - pluginManagement
     - Plugin 설정관리
   * - modules
     - 하위 프로젝트 관, 신규 WEB / RESTFul 프로젝트 추가 시 modules에 등록


H2 Database 삭제, Example 프로젝트 삭제
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

..

   H2 Database Driver 삭제 방법 - sample-commons 프로젝트의 pom.xml 파일 29라인의 아래 내용 삭제.


.. code-block:: xml

           <dependency>
               <groupId>com.kthcorp</groupId>
               <artifactId>kthcorp-starter-database-h2</artifactId>
           </dependency>

..

    Example 프로젝트 삭제 방법 - sample-webapp 프로젝트의 pom.xml 파일 35라인의 아래 내용 삭제.


.. code-block:: xml

           <dependency>
               <groupId>com.kthcorp.archetype</groupId>
               <artifactId>kthcorp-base-example</artifactId>
           </dependency>

Database Driver 추가 방법
^^^^^^^^^^^^^^^^^^^^^^^^^

..

   sample-commons 프로젝트의 pom.xml 파일에 드라이버 추가.

   ..

      MySQL JDBC Driver


.. code-block:: xml

           <dependency>
               <groupId>com.kthcorp</groupId>
               <artifactId>kthcorp-starter-database-mysql</artifactId>
           </dependency>

..

   ..

      MariaDB JDBC Driver


.. code-block:: xml

           <dependency>
               <groupId>com.kthcorp</groupId>
               <artifactId>kthcorp-starter-database-mariadb</artifactId>
           </dependency>

..

   ..

      PostgreSQL JDBC Driver


.. code-block:: xml

           <dependency>
               <groupId>com.kthcorp</groupId>
               <artifactId>kthcorp-starter-database-postgresql</artifactId>
           </dependency>

DataSource 변경 방법
^^^^^^^^^^^^^^^^^^^^

..

   sample-commons 프로젝트의 src/main/resources/application-dataSource.yml 파일 수정.\ :raw-html-m2r:`<br />`
   아래에서 comment, driver, url, username, password를 프로젝트에서 사용되는 Database 정보로 변경


.. code-block:: yaml

   dataSource:
     master:
       comment: "Test MySQL MASTER Database"
       encrypt-key:
       iv-spec:
       driver: "com.mysql.jdbc.Driver"
       url: "jdbc:mysql://localhost:3306/test?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8"
       username: "test"
       password: "test"
       validation-query: "SELECT 1"
       init-size: 1
       min-idle:  2
       max-idle:  5
       max-total: 10
       max-wait:  5000
     slave:
       comment: "Test MySQL SLAVE Database"
       encrypt-key:
       iv-spec:
       driver: "com.mysql.jdbc.Driver"
       url: "jdbc:mysql://localhost:3306/test?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8"
       username: "test"
       password: "test"
       validation-query: "SELECT 1"
       init-size: 1
       min-idle:  2
       max-idle:  5
       max-total: 10
       max-wait:  5000

Response Message 추가 및 사용 방법
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

..

   sample-commons 프로젝트의 src/main/resources/application-response.yml 파일에 추가 후 아래와 같이 사용.


.. code-block:: java

   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;

   import com.kthcorp.commons.yaml.property.ResponseStatusProperty;

   import lombok.extern.slf4j.Slf4j;

   @Slf4j
   @Service
   public class TestService {

       @Autowired
       private ResponseStatusProperty responseStatusProperty;

       public void test() {
           log.debug(responseStatusProperty.get(200));
       }

   }

Property 추가 및 사용 방법
^^^^^^^^^^^^^^^^^^^^^^^^^^

..

   sample-commons 프로젝트의 src/main/resources/com/sample/commons/config.yml 파일에 추가 후 아래와 같이 사용.


.. code-block:: java

   import org.springframework.stereotype.Service;

   import com.kthcorp.archetype.commons.config.Property;

   import lombok.extern.slf4j.Slf4j;

   @Slf4j
   @Service
   public class TestService {

       public void test() {
           log.debug(Property.get("test"));
       }

   }

Maven을 이용한 빌드
^^^^^^^^^^^^^^^^^^^

..

   로컬에 Maven이 설치 되어있거나 Eclipse에서 빌드 방법 : Parent 프로젝트로 이동 후 아래 명령어 이용.


.. code-block:: bash

   # 터미널에서 빌드
   mvn -P=dev clean install

   # Eclipse에서 빌드
   -P=dev clean install

..

   로컬에 Maven이 설치되어있지 않은 경우의 빌드 방법.


.. code-block:: bash

   # 터미널에서 빌드
   chmod +x mvnw
   ./mvnw -P=dev clean install
