"use client"

import { useState } from "react"
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import "bootstrap/dist/css/bootstrap.min.css"
import "./App.css"
import Navbar from "./components/Navbar"
import FolderList from "./components/FolderList"
import ItemList from "./components/ItemList"

function App() {
  const [selectedFolder, setSelectedFolder] = useState(null)

  return (
    <Router>
      <div className="App">
        <Navbar />
        <div className="container-fluid">
          <div className="row">
            <div className="col-md-3 sidebar">
              <FolderList selectedFolder={selectedFolder} setSelectedFolder={setSelectedFolder} />
            </div>
            <div className="col-md-9 main-content">
              <Routes>
                <Route path="/folders/:folderId/items" element={<ItemList setSelectedFolder={setSelectedFolder} />} />
                <Route
                  path="/"
                  element={
                    <div className="text-center mt-5">
                      <h2>Selecciona una carpeta para ver sus elementos</h2>
                    </div>
                  }
                />
              </Routes>
            </div>
          </div>
        </div>
      </div>
    </Router>
  )
}

export default App
