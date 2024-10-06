function fn() {
  var env = karate.env; // obtener la propiedad del sistema 'karate.env'
  karate.log('karate.env system property was:', env);

  // Si no hay un valor para 'env', se asigna por defecto 'staging'
  if (!env) {
    env = 'staging';
  }

  // Configuración inicial
  var config = {
    // Aquí puedes definir variables generales y rutas de API
    baseUrl: 'https://petstore.swagger.io',  // Variable para la URL base
    someVar: 'defaultValue'  // Ejemplo de una variable adicional
  }

  // Personalización según el entorno
  if (env == 'prod') {
    config.baseUrl = 'https://petstore.swagger.io';
  } else if (env == 'staging') {
    config.baseUrl = 'https://petstore.swagger.io';
  }

  // Configurar tiempos de espera
  karate.configure('connectTimeout', 5000);  // Tiempo máximo para conectar
  karate.configure('readTimeout', 5000);  // Tiempo máximo para leer la respuesta

  return config;
}
