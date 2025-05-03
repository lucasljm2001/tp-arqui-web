"use client"

import { useNavigate } from "react-router-dom"
import FolderForm from "./FolderForm"
import FolderItem from "./FolderItem"

function FolderList({
  folders,
  loading,
  error,
  selectedFolder,
  setSelectedFolder,
  onCreateFolder,
  onDeleteFolder,
  onSortChange,
  sortBy,
  direction,
}) {
  const navigate = useNavigate()

  // Seleccionar una carpeta
  const handleSelectFolder = (folder) => {
    setSelectedFolder(folder)
    navigate(`/folders/${folder.folder_id}/items`)
  }

  const handleDeleteFolder = (folderId) =>{
    onDeleteFolder(folderId)
    if (selectedFolder && selectedFolder.folder_id === folderId) {
      navigate("/")
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
              <button className="dropdown-item" onClick={() => onSortChange("name")}>
                Por nombre {sortBy === "name" && (direction === "asc" ? "↑" : "↓")}
              </button>
            </li>
            <li>
              <button className="dropdown-item" onClick={() => onSortChange("itemCount")}>
                Por cantidad {sortBy === "itemCount" && (direction === "asc" ? "↑" : "↓")}
              </button>
            </li>
          </ul>
        </div>
      </div>

      <FolderForm onSubmit={onCreateFolder} />

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
                onDelete={handleDeleteFolder}
              />
            ))
          )}
        </div>
      )}
    </div>
  )
}

export default FolderList
