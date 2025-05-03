"use client"

import { useState, useEffect } from "react"
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import "bootstrap/dist/css/bootstrap.min.css"
import "./App.css"
import Navbar from "./components/Navbar"
import FolderList from "./components/FolderList"
import ItemList from "./components/ItemList"
import { folderAPI } from "./api/api"

function App() {
  const [selectedFolder, setSelectedFolder] = useState(null)
  const [folders, setFolders] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [sortBy, setSortBy] = useState("name")
  const [direction, setDirection] = useState("asc")

  // Cargar carpetas
  const loadFolders = async () => {
    try {
      setLoading(true)
      const response = await folderAPI.getFolders(sortBy, direction)
      setFolders(response.data)
      setError(null)
    } catch (err) {
      setError("Error al cargar las carpetas")
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadFolders()
  }, [sortBy, direction])

  // Crear una nueva carpeta
  const handleCreateFolder = async (name) => {
    try {
      await folderAPI.createFolder(name)
      loadFolders()
    } catch (err) {
      setError("Error al crear la carpeta")
      console.error(err)
    }
  }

  // Eliminar una carpeta
  const handleDeleteFolder = async (folderId) => {
    try {
      await folderAPI.deleteFolder(folderId)
      loadFolders()
      if (selectedFolder && selectedFolder.folder_id === folderId) {
        setSelectedFolder(null)
      }
    } catch (err) {
      setError("Error al eliminar la carpeta")
      console.error(err)
    }
  }

  // Actualizar una carpeta
  const handleUpdateFolder = async (folderId, name) => {
    try {
      await folderAPI.updateFolder(folderId, name)
      loadFolders()

      // Actualizar también la carpeta seleccionada si es la que se modificó
      if (selectedFolder && selectedFolder.folder_id === folderId) {
        setSelectedFolder({ ...selectedFolder, name })
      }
    } catch (err) {
      setError("Error al actualizar la carpeta")
      console.error(err)
    }
  }

  // Cambiar el orden de las carpetas
  const handleSortFolders = (newSortBy) => {
    if (sortBy === newSortBy) {
      setDirection(direction === "asc" ? "desc" : "asc")
    } else {
      setSortBy(newSortBy)
      setDirection("asc")
    }
  }

  return (
    <Router>
      <div className="App">
        <Navbar />
        <div className="container-fluid">
          <div className="row">
            <div className="col-md-3 sidebar">
              <FolderList
                folders={folders}
                loading={loading}
                error={error}
                selectedFolder={selectedFolder}
                setSelectedFolder={setSelectedFolder}
                onCreateFolder={handleCreateFolder}
                onDeleteFolder={handleDeleteFolder}
                onUpdateFolder={handleUpdateFolder}
                onSortChange={handleSortFolders}
                sortBy={sortBy}
                direction={direction}
              />
            </div>
            <div className="col-md-9 main-content">
              <Routes>
                <Route
                  path="/folders/:folderId/items"
                  element={
                    <ItemList
                      setSelectedFolder={setSelectedFolder}
                      onUpdateFolder={handleUpdateFolder}
                      onReloadFolders={loadFolders}
                    />
                  }
                />
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
