import logo from "./logo.svg";
import "./App.css";
import Sidebar from "./components/Sidebar";
import Bill from "./components/Bill";

function App() {
  return (
    <div className="App">
      <Sidebar></Sidebar>
      <Bill></Bill>
    </div>
  );
}

export default App;
