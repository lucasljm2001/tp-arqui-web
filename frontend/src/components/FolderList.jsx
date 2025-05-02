"use client"

import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { folderAPI } from "../api/api"
import FolderForm from "./FolderForm"
// Añadir la importación del nuevo componente FolderItem
import FolderItem from "./FolderItem"

function FolderList({ selectedFolder, setSelectedFolder }) {
  const [folders, setFolders] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [sortBy, setSortBy] = useState("name")
  const [direction, setDirection] = useState("asc")
  const navigate = useNavigate()

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

  // Seleccionar una carpeta
  const handleSelectFolder = (folder) => {
    setSelectedFolder(folder)
    navigate(`/folders/${folder.folder_id}/items`)
  }

  // Eliminar una carpeta
  const handleDeleteFolder = async (folderId, e) => {
    e.stopPropagation()
    if (window.confirm("¿Estás seguro de que quieres eliminar esta carpeta?")) {
      try {
        await folderAPI.deleteFolder(folderId)
        loadFolders()
        if (selectedFolder && selectedFolder.folder_id === folderId) {
          console.log("entraa")
          setSelectedFolder(null)
          navigate("/")
        }
      } catch (err) {
        setError("Error al eliminar la carpeta")
        console.error(err)
      }
    }
  }

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

  // Cambiar el orden de las carpetas
  const handleSort = (newSortBy) => {
    if (sortBy === newSortBy) {
      setDirection(direction === "asc" ? "desc" : "asc")
    } else {
      setSortBy(newSortBy)
      setDirection("asc")
    }
  }

  return (
    <div className="folder-list p-3">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h3>Carpetas</h3>
        <div className="dropdown">
          <button
            className="btn btn-sm btn-outline-secondary dropdown-toggle"
            type="button"
            id="sortDropdown"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            Ordenar
          </button>
          <ul className="dropdown-menu" aria-labelledby="sortDropdown">
            <li>
              <button className="dropdown-item" onClick={() => handleSort("name")}>
                Por nombre {sortBy === "name" && (direction === "asc" ? "↑" : "↓")}
              </button>
            </li>
            <li>
              <button className="dropdown-item" onClick={() => handleSort("itemCount")}>
                Por cantidad {sortBy === "itemCount" && (direction === "asc" ? "↑" : "↓")}
              </button>
            </li>
          </ul>
        </div>
      </div>

      <FolderForm onSubmit={handleCreateFolder} />

      {error && <div className="alert alert-danger">{error}</div>}

      {loading ? (
        <div className="text-center mt-3">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Cargando...</span>
          </div>
        </div>
      ) : (
        <div className="list-group mt-3">
          {folders.length === 0 ? (
            <div className="text-center text-muted">No hay carpetas</div>
          ) : (
            folders.map((folder) => (
              <FolderItem
                key={folder.folder_id}
                folder={folder}
                isSelected={selectedFolder && selectedFolder.folder_id === folder.folder_id}
                onSelect={handleSelectFolder}
                onDelete={(folderId) => handleDeleteFolder(folderId, event)}
              />
            ))
          )}
        </div>
      )}
    </div>
  )
}

export default FolderList
