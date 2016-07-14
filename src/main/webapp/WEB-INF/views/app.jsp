<html>
<head>
    <base href="/">
    <title>Angular 2 QuickStart</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="styles.css">

    <!-- Polyfill(s) for older browsers -->
    <script src="/resources/angular2app/node_modules/core-js/client/shim.min.js"></script>

    <script src="/resources/angular2app/node_modules/zone.js/dist/zone.js"></script>
    <script src="/resources/angular2app/node_modules/reflect-metadata/Reflect.js"></script>
    <script src="/resources/angular2app/node_modules/systemjs/dist/system.src.js"></script>

    <script src="/resources/angular2app/systemjs.config.js"></script>
    <script>
        System.import('app').catch(function (err) {
            console.error(err);
        });
    </script>
</head>

<body>
<my-app>Loading...</my-app>
</body>
</html>
