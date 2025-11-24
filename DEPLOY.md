# Guía de Despliegue - OptiPlan

Esta guía te ayudará a desplegar OptiPlan en diferentes plataformas cloud de forma rápida y sencilla.

## 📋 Requisitos previos

1. Cuenta de GitHub
2. Git instalado en tu computadora
3. Tu código debe estar funcionando localmente

## 🚀 Opción 1: Railway (Recomendada)

Railway es la opción más fácil y rápida.

### Pasos:

1. **Crear repositorio en GitHub:**
   ```bash
   cd "c:\Users\Mi Equipo\OneDrive\Escritorio\web"
   git init
   git add .
   git commit -m "Initial commit"
   ```

2. **Subir a GitHub:**
   - Crea un nuevo repositorio en https://github.com/new
   - Ejecuta:
   ```bash
   git remote add origin https://github.com/TU-USUARIO/optiplan.git
   git branch -M main
   git push -u origin main
   ```

3. **Desplegar en Railway:**
   - Ve a https://railway.app
   - Click en "Start a New Project"
   - Selecciona "Deploy from GitHub repo"
   - Elige tu repositorio "optiplan"
   - Railway detectará automáticamente Spring Boot

4. **Agregar base de datos MySQL:**
   - En tu proyecto de Railway, click en "+ New"
   - Selecciona "Database" → "Add MySQL"
   - Railway creará automáticamente la base de datos

5. **Configurar variables de entorno:**
   - Click en tu servicio web
   - Ve a "Variables"
   - Agrega estas variables (Railway te dará los valores de MySQL automáticamente):
   ```
   SPRING_PROFILES_ACTIVE=prod
   DATABASE_URL=jdbc:mysql://[MYSQL_HOST]:[MYSQL_PORT]/railway
   DATABASE_USERNAME=[MYSQL_USER]
   DATABASE_PASSWORD=[MYSQL_PASSWORD]
   ```

6. **¡Listo!** Railway te dará una URL pública como: `https://tu-app.up.railway.app`

---

## 🌐 Opción 2: Render

### Pasos:

1. **Sube tu código a GitHub** (mismos pasos que Railway)

2. **Crear cuenta en Render:**
   - Ve a https://render.com
   - Crea una cuenta con GitHub

3. **Crear Web Service:**
   - Click en "New +" → "Web Service"
   - Conecta tu repositorio de GitHub
   - Configura:
     - **Name:** optiplan
     - **Environment:** Java
     - **Build Command:** `mvn clean package -DskipTests`
     - **Start Command:** `java -Dspring.profiles.active=prod -jar target/web-0.0.1-SNAPSHOT.jar`

4. **Agregar base de datos:**
   - Click en "New +" → "PostgreSQL" (gratis en Render)
   - O usa MySQL externo (como PlanetScale)

5. **Variables de entorno:**
   ```
   SPRING_PROFILES_ACTIVE=prod
   DATABASE_URL=[tu-url-de-base-de-datos]
   DATABASE_USERNAME=[usuario]
   DATABASE_PASSWORD=[contraseña]
   ```

6. **Deploy:** Click en "Create Web Service"

---

## ☁️ Opción 3: Heroku

### Pasos:

1. **Instala Heroku CLI:**
   - Descarga desde: https://devcenter.heroku.com/articles/heroku-cli

2. **Login y crear app:**
   ```bash
   heroku login
   heroku create nombre-de-tu-app
   ```

3. **Agregar MySQL (ClearDB):**
   ```bash
   heroku addons:create cleardb:ignite
   ```

4. **Obtener URL de base de datos:**
   ```bash
   heroku config:get CLEARDB_DATABASE_URL
   ```

5. **Configurar variables:**
   ```bash
   heroku config:set SPRING_PROFILES_ACTIVE=prod
   heroku config:set DATABASE_URL=[url-cleardb]
   ```

6. **Desplegar:**
   ```bash
   git add .
   git commit -m "Deploy to Heroku"
   git push heroku main
   ```

7. **Ver tu app:**
   ```bash
   heroku open
   ```

---

## 🗄️ Opciones de Base de Datos Gratuitas

Si necesitas MySQL externo:

- **PlanetScale:** https://planetscale.com (MySQL gratuito, muy bueno)
- **Railway MySQL:** Incluido en Railway
- **Clever Cloud:** https://www.clever-cloud.com
- **FreeSQLDatabase:** https://www.freesqldatabase.com

---

## ✅ Verificar el despliegue

Una vez desplegado, verifica:

1. Accede a tu URL pública
2. Intenta registrarte
3. Crea una tarea
4. Prueba las notificaciones

---

## 🐛 Problemas comunes

**Error: "Port 8080 already in use"**
- Solución: El archivo `application-prod.properties` ya usa `${PORT:8080}` que se configura automáticamente

**Error de base de datos**
- Verifica que las variables de entorno estén correctas
- Asegúrate de que la URL incluya `?useSSL=false&serverTimezone=UTC`

**Error de compilación**
- Verifica que `system.properties` tenga Java 17
- Revisa los logs de build en tu plataforma

---

## 📝 Notas importantes

- El perfil de producción (`prod`) tiene caching activado y menos logs
- Las notificaciones funcionan con HTTPS (necesario para navegadores)
- La primera vez puede tardar unos minutos en iniciar

---

## 🎉 ¡Felicidades!

Tu aplicación OptiPlan ahora está disponible públicamente. Comparte la URL con quien quieras.

**URLs de ejemplo:**
- Railway: `https://optiplan.up.railway.app`
- Render: `https://optiplan.onrender.com`
- Heroku: `https://optiplan.herokuapp.com`
