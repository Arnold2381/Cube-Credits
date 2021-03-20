import React from "react";
import "./Card.css";
import Grocery from "../pics/grocery.svg";
import Coin from "../pics/coin.svg";
import Notes from "../pics/note.svg";
function Card() {
  return (
    <div className="card">
      <img src={Grocery} className="img"></img>
      <a className="text">Brought grocery items</a>
      <a className="text1">Cube Bazaar - 28 Feb 21:02 pm</a>
      <img src={Coin} className="coin"></img>
      <a className="coincredit">+10</a>
      <img src={Notes} className="notes"></img>
      <a className="moneycost">Rs 200</a>
    </div>
  );
}

export default Card;
