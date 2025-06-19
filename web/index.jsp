<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<!--
    CateringTacna
    Adaptado para servicio de catering personalizado
-->
<html>
<head>
    <title>CateringTacna - Tu Catering Personalizado</title>
    <meta charset="utf-8" />
    <meta name="robots" content="index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="assets/css/main.css" />
</head>
<body>

    <!-- Header -->
    <header id="header">
        <div class="logo"><a href="index.jsp">CateringTacna <span>Tu Catering Personalizado</span></a></div>
        <a href="#menu"><span>Menú</span></a>
    </header>

    <!-- Nav -->
    <nav id="menu">
        <ul class="links">
            <li><a href="Login.jsp">Iniciar Sesión</a></li>
            <li><a href="galeria.jsp">Galería</a></li>
        </ul>
    </nav>

    <!-- Banner -->
    <section id="banner" class="bg-img" data-bg="banner.jpg">
        <div class="inner">
            <header>
                <h1>Bienvenido a CateringTacna</h1>
                <p>Disfruta de alimentos saludables y deliciosos para desayuno, almuerzo y cena, ¡directo a tu puerta!</p>
            </header>
        </div>
        <a href="#one" class="more">Conoce Más</a>
    </section>

    <!-- One -->
    <section id="one" class="wrapper post bg-img" data-bg="banner2.jpg">
        <div class="inner">
            <article class="box">
                <header>
                    <h2>Servicio Personalizado</h2>
                    <p>Elige lo que quieres para tus comidas diarias</p>
                </header>
                <div class="content">
                    <p>En CateringTacna te ofrecemos un servicio a tu medida, donde puedes seleccionar platos para desayuno, almuerzo y cena según tus gustos y necesidades nutricionales.</p>
                    <p>Nuestros menús incluyen opciones saludables, tradicionales y con grasas controladas, todo preparado con ingredientes frescos y de calidad.</p>
                </div>
            </article>
        </div>
        <a href="#two" class="more">Siguiente</a>
    </section>

    <!-- Two -->
    <section id="two" class="wrapper post bg-img" data-bg="banner5.jpg">
        <div class="inner">
            <article class="box">
                <header>
                    <h2>Pedidos Flexibles</h2>
                    <p>Ordena diario o semanalmente</p>
                </header>
                <div class="content">
                    <p>Realiza pedidos diarios o semanales, elige la fecha de entrega que mejor te convenga y recibe tus comidas directamente en tu domicilio.</p>
                    <p>Gestiona tu cuenta fácilmente y mantente al tanto del estado de tus pedidos y pagos.</p>
                </div>
                <footer><a href="Login.jsp" class="button alt">Inicia Sesión para Pedir</a></footer>
            </article>
        </div>
        <a href="#three" class="more">Siguiente</a>
    </section>

    <!-- Three -->
    <section id="three" class="wrapper post bg-img" data-bg="banner4.jpg">
        <div class="inner">
            <article class="box">
                <header>
                    <h2>Calidad y Variedad</h2>
                    <p>Platos preparados con cariño y dedicación</p>
                </header>
                <div class="content">
                    <p>Nuestro catálogo de platos incluye desayunos energéticos, almuerzos completos y cenas ligeras, todos diseñados para brindarte una alimentación balanceada y deliciosa.</p>
                    <p>Explora nuestra galería y déjate tentar por nuestras propuestas culinarias.</p>
                </div>
                <footer><a href="galeria.jsp" class="button alt">Ver Galería</a></footer>
            </article>
        </div>
        <a href="#four" class="more">Siguiente</a>
    </section>

    <!-- Four -->
    <section id="four" class="wrapper post bg-img" data-bg="banner3.jpg">
        <div class="inner">
            <article class="box">
                <header>
                    <h2>Contacto</h2>
                    <p>Estamos aquí para ayudarte</p>
                </header>
                <div class="content">
                    <p>¿Tienes alguna consulta o necesitas asistencia? Contáctanos, queremos escuchar de ti para mejorar nuestro servicio.</p>
                </div>
                <footer><a href="contacto.jsp" class="button alt">Contáctanos</a></footer>
            </article>
        </div>
    </section>

    <!-- Footer -->
  <footer id="footer">
    <div class="inner">
        <h2>Contacto</h2>
        <form action="EnviarWhatsAppServlet" method="post">
            <div class="field half first">
                <label for="name">Nombre</label>
                <input name="name" id="name" type="text" placeholder="Tu nombre" required />
            </div>
            <div class="field half">
                <label for="email">Número de WhatsApp (con +51...)</label>
                <input name="email" id="email" type="tel" placeholder="+51 999999999" required style="color: black;" />

            </div>
            <div class="field">
                <label for="message">Mensaje</label>
                <textarea name="message" id="message" rows="6" placeholder="Escribe tu mensaje aquí" required></textarea>
            </div>
            <ul class="actions">
                <li><input value="Enviar por WhatsApp" class="button alt" type="submit" /></li>
            </ul>
        </form>

        <ul class="icons">
            <li><a href="#" class="icon round fa-twitter"><span class="label">Twitter</span></a></li>
            <li><a href="#" class="icon round fa-facebook"><span class="label">Facebook</span></a></li>
            <li><a href="#" class="icon round fa-instagram"><span class="label">Instagram</span></a></li>
        </ul>
    </div>
</footer>


    <div class="copyright">
        Sitio web creado con: <a href="https://templated.co/">Templated</a> Distribuido por <a href="https://themewagon.com/">ThemeWagon</a>
    </div>

    <!-- Scripts -->
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/jquery.scrolly.min.js"></script>
    <script src="assets/js/jquery.scrollex.min.js"></script>
    <script src="assets/js/skel.min.js"></script>
    <script src="assets/js/util.js"></script>
    <script src="assets/js/main.js"></script>
</body>
</html>
