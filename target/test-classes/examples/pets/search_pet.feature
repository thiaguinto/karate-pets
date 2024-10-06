Feature: Search Pet

  Background:
    * url 'https://petstore.swagger.io'

  Scenario: Manage a pet from JSON
    # 1. Leer los datos de un archivo JSON
    * def add_a_pet_request = read('petData.json')

    # 2. Añadir una mascota a la tienda
    Given path '/v2/pet'
    And request add_a_pet_request
    When method post
    Then status 200

    * def petId = response.id
    * print 'pet id:', petId

    # 3. Consultar la mascota ingresada previamente (Búsqueda por ID)
    Given path '/v2/pet/', petId
    When method get
    Then status 200
    And match response.id == petId

    # 4. Actualizar la mascota
    * add_a_pet_request.name = 'doggie-updated'
    * add_a_pet_request.status = 'sold'

    Given path '/v2/pet'
    And request add_a_pet_request
    When method put
    Then status 200

    # 5. Consultar la mascota modificada por estatus (Búsqueda por estatus)
    Given path '/v2/pet/findByStatus'
    And param status = 'sold'
    When method get
    Then status 200
    And match response[0].status == 'sold'
