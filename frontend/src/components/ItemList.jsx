"use client"

import { useState, useEffect } from "react"
import { useParams } from "react-router-dom"
import { folderAPI, itemAPI } from "../api/api"
import ItemForm from "./ItemForm"
// Cambiar la importación de ItemItem a ItemRow
import ItemRow from "./ItemRow"
// Añadir las importaciones de los nuevos componentes
import FolderHeader from "./FolderHeader"
import ItemControls from "./ItemControls"

function ItemList({ setSelectedFolder }) {
  const { folderId } = useParams()
  const [items, setItems] = useState([])
  const [folder, setFolder] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [editingFolder, setEditingFolder] = useState(false)
  const [editingItem, setEditingItem] = useState(null)
  const [sortBy, setSortBy] = useState("createdAt")
  const [direction, setDirection] = useState("desc")
  const [filter, setFilter] = useState("")

  // Cargar carpeta y elementos
  const loadFolderAndItems = async () => {
    try {
      setLoading(true)

      // Cargar todas las carpetas para obtener la actual
      const foldersResponse = await folderAPI.getFolders()
      const currentFolder = foldersResponse.data.find((f) => f.folder_id === Number.parseInt(folderId))

      if (currentFolder) {
        setFolder(currentFolder)
        setSelectedFolder(currentFolder)

        // Cargar elementos de la carpeta
        let state1 = "",
          state2 = ""
        if (filter === "TODO") {
          state1 = "TODO"
        } else if (filter === "DONE") {
          state1 = "DONE"
        }

        const itemsResponse = await itemAPI.getItems(folderId, sortBy, direction, state1, state2)
        setItems(itemsResponse.data)
      } else {
        setError("Carpeta no encontrada")
      }
    } catch (err) {
      setError("Error al cargar los datos")
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadFolderAndItems()
  }, [folderId, sortBy, direction, filter])

  // Actualizar nombre de carpeta
  const handleUpdateFolder = async (name) => {
    try {
      await folderAPI.updateFolder(folderId, name)
      setEditingFolder(false)
      loadFolderAndItems()
    } catch (err) {
      setError("Error al actualizar la carpeta")
      console.error(err)
    }
  }

  // Crear un nuevo elemento
  const handleCreateItem = async (description) => {
    try {
      await itemAPI.createItem(folderId, description)
      loadFolderAndItems()
    } catch (err) {
      setError("Error al crear el elemento")
      console.error(err)
    }
  }

  // Actualizar un elemento
  const handleUpdateItem = async (itemId, description) => {
    try {
      await itemAPI.updateItem(itemId, description)
      setEditingItem(null)
      loadFolderAndItems()
    } catch (err) {
      setError("Error al actualizar el elemento")
      console.error(err)
    }
  }

  // Cambiar estado de un elemento
  const handleToggleItem = async (itemId) => {
    try {
      await itemAPI.toggleItem(itemId)
      loadFolderAndItems()
    } catch (err) {
      setError("Error al cambiar el estado del elemento")
      console.error(err)
    }
  }

  // Eliminar un elemento
  const handleDeleteItem = async (itemId) => {
    if (window.confirm("¿Estás seguro de que quieres eliminar este elemento?")) {
      try {
        await itemAPI.deleteItem(itemId)
        loadFolderAndItems()
      } catch (err) {
        setError("Error al eliminar el elemento")
        console.error(err)
      }
    }
  }

  // Cambiar el orden de los elementos
  const handleSort = (newSortBy) => {
    if (sortBy === newSortBy) {
      setDirection(direction === "asc" ? "desc" : "asc")
    } else {
      setSortBy(newSortBy)
      setDirection("asc")
    }
  }

  return (
    <div className="item-list p-3">
      {loading ? (
        <div className="text-center mt-5">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Cargando...</span>
          </div>
        </div>
      ) : error ? (
        <div className="alert alert-danger">{error}</div>
      ) : (
        <>
          <div className="d-flex justify-content-between align-items-center mb-4">
            <FolderHeader
              folder={folder}
              isEditing={editingFolder}
              onEdit={() => setEditingFolder(true)}
              onUpdate={handleUpdateFolder}
              onCancelEdit={() => setEditingFolder(false)}
            />

            <ItemControls filter={filter} onFilterChange={setFilter} sortBy={sortBy} onSortChange={handleSort} />
          </div>

          <ItemForm onSubmit={handleCreateItem} />

          <div className="list-group mt-4">
            {items.length === 0 ? (
              <div className="text-center text-muted">No hay elementos en esta carpeta</div>
            ) : (
              items.map((item) => (
                <ItemRow
                  key={item.item_id}
                  item={item}
                  isEditing={editingItem === item.item_id}
                  onToggle={handleToggleItem}
                  onEdit={setEditingItem}
                  onDelete={handleDeleteItem}
                  onUpdate={handleUpdateItem}
                  onCancelEdit={() => setEditingItem(null)}
                />
              ))
            )}
          </div>
        </>
      )}
    </div>
  )
}

export default ItemList
