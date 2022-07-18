const mysqlConnection = require("../util/database");
const config = require("../config")
const controller = {};
controller.list = (req, res) => {

    mysqlConnection.query("SELECT id, Movimiento, Pulso, (DATE_FORMAT(Date, '%Y-%m-%d %T')) as fecha , Tipo FROM tblsensor where Tipo=1 order by date desc", (err, rows, fields) => {
        if (!err) {
            res.json({
                "code": 200,
                "error": false,
                "message": "Sensor Registrado",
                "users": rows
            });

        } else {
            res.json({
                "code": 500,
                "error": true,
                "message": err
            });
        }

    });
};

controller.list1 = (req, res) => {

    mysqlConnection.query("SELECT Pulso from tblsensor where Tipo=2 order by date desc limit 1", (err, rows, fields) => {
        if (!err) {
			if(rows.length> 0){
				var pulsoDato = rows[0].Pulso
				res.json({"Pulso" : pulsoDato });				
			}else{
				res.json({"Pulso" : "3" });
			}
        } else {
			res.json({"Pulso" : "3" });
        }

    });
};

controller.add = (req, res) => {
    const { Movimiento} = req.body;
    console.log(Movimiento);
    const srtquery = `INSERT INTO tblsensor(Movimiento, Pulso, Tipo ) VALUES(?,1,1)`;
    mysqlConnection.query(srtquery, [Movimiento], (err, rows, fields) => {
        if (err) {
            res.json({
                "code": 500,
                "error": true,
                "message": err
            });
        } else {
            res.json({
                "code": 200,
                "error": false,
                "message": "Leido motion"
            })
        }
    });

}
controller.add2 = (req, res) => {
    const { Pulso} = req.body;
    console.log("Enviado desde Android: " + Pulso);
    const srtquery = `INSERT INTO tblsensor(Pulso, Tipo) VALUES(?,2)`;
    mysqlConnection.query(srtquery, [Pulso], (err, rows, fields) => {
        if (err) {
            res.json({
                "code": 500,
                "error": true,
                "message": err
            });
        } else {
            res.json({
                "code": 200,
                "error": false,
                "message": "Leido"
            })
        }
    });
}
module.exports = controller