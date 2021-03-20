import firebase from "firebase/app";

const config = {
  apiKey: "AIzaSyBZXsyoTWVDe3TNZFKAvlih0xInDm9eaUo",
  authDomain: "cube-credits.firebaseapp.com",
  databaseURL: "https://cube-credits-default-rtdb.firebaseio.com",
  projectId: "cube-credits",
  storageBucket: "cube-credits.appspot.com",
  messagingSenderId: "736272055256",
  appId: "1:736272055256:web:5fee936c6e818c3fe0d0ad",
  measurementId: "G-1EZG581Q1H",
};
firebase.initializeApp(config);
export default firebase;
