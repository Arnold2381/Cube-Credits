import React from "react";
import "./Sidebar.css";
import Stocks from "../pics/stock.svg";
import Customer from "../pics/customer.svg";
import Logo from "../pics/logo.svg";
function Sidebar() {
  return (
    <div>
      <div className="back">
        <div className="logo">
          <img src={Logo}></img>
        </div>
        <div className="customer">
          <button className="button">
            <img src={Customer}></img>
          </button>
          <button className="button">
            <img src={Stocks}></img>
          </button>
        </div>
      </div>
    </div>
  );
}
export default Sidebar;
