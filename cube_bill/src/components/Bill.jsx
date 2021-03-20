import React from "react";
import Sidebar from "./Sidebar";
import Balance from "../pics/balance.svg";
import "./Bill.css";
import Total from "../pics/total.svg";
import Cash from "../pics/cash.svg";
import Earned from "../pics/earned.svg";
import Purchase from "../pics/purchase.svg";
import Card from "./Card";
import Pocket from "../pics/pocket.svg";
import firebase from "../firebase";
import { database } from "../../node_modules/firebase";
import { useState, useEffect, Fragment } from "react";
const Progress = ({ done }) => {
  const [style, setStyle] = React.useState({});

  setTimeout(() => {
    const newStyle = {
      opacity: 1,
      width: `${done}%`,
    };

    setStyle(newStyle);
  }, 200);

  return (
    <div className="progress">
      <div className="progress-done" style={style}>
        <a className="percentage">{done}%</a>
      </div>
    </div>
  );
};
function execute() {
  const ref = firebase.database().ref("Billing").child("Abhishek123");
  console.log(ref);
  // ref.on("value", (snap) => {
  //   console.log(snap.val());
  // });
}
function Bill() {
  execute();
  return (
    <div>
      <div className="balance">
        <img src={Balance}></img>
        <a className="credits">150</a>
      </div>
      <div className="balance1">
        <img src={Total}></img>
        <a className="credits">Rs 150</a>
      </div>
      <div className="line"></div>
      <div>
        <img src={Pocket} className="pocket"></img>
        <Progress done="50"></Progress>
      </div>
      <div className="cardcontainer">
        <Card></Card>
        <Card></Card>
        <Card></Card>
      </div>
      <div className="cash">
        <img src={Cash}></img>
      </div>
      <div className="earned">
        <img src={Earned}></img>
        <a className="money">150</a>
      </div>
      <div className="purchase">
        <img src={Purchase}></img>
        <a className="money">Rs 150</a>
      </div>
    </div>
  );
}
export default Bill;
