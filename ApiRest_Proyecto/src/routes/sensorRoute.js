const express = require("express");

const sensorController = require("../controllers/sensorController")
const router = express.Router();
router.get("/",sensorController.list);
router.get("/pulso",sensorController.list1)
router.post("/create", sensorController.add);
router.post("/led/", sensorController.add2);

module.exports = router;
