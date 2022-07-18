const express = require("express");
const config = require("./config.json");
const app = express();

app.set('port', process.env.PORT || config.PORT)

app.use(express.json());

app.use(express.urlencoded({ extended: false }));

const sensorRoutes = app.use(require("./routes/sensorroute"))

app.listen(app.get('port'), () => {
    console.log(`Servidor en puerto http://localhost:${app.get('port')}`);
});
