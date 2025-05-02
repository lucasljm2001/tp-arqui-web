import axios from "axios"

// Crear una instancia de axios con la URL base
const api = axios.create({
  baseURL: "http://localhost:8080",
})

// Funciones para manejar carpetas (folders)
export const folderAPI = {
  // Obtener todas las carpetas
  getFolders: (sortBy = "name", direction = "asc") => {
    return api.get(`/folders?sortBy=${sortBy}&direction=${direction}`)
  },

  // Crear una nueva carpeta
  createFolder: (name) => {
    return api.post("/folders", { name })
  },

  // Eliminar una carpeta
  deleteFolder: (folderId) => {
    return api.delete(`/folders/${folderId}`)
  },

  // Actualizar el nombre de una carpeta
  updateFolder: (folderId, name) => {
    return api.patch(`/folders/${folderId}`, { name })
  },
}

// Funciones para manejar elementos (items)
export const itemAPI = {
  // Obtener elementos de una carpeta
  getItems: (folderId, sortBy = "createdAt", direction = "desc", state1 = "", state2 = "") => {
    let url = `/folders/${folderId}/items?sortBy=${sortBy}&direction=${direction}`
    if (state1) url += `&state1=${state1}`
    if (state2) url += `&state2=${state2}`
    return api.get(url)
  },

  // Crear un nuevo elemento en una carpeta
  createItem: (folderId, description) => {
    return api.post(`/folders/${folderId}/items`, { description })
  },

  // Actualizar la descripción de un elemento
  updateItem: (itemId, description) => {
    return api.patch(`/items/${itemId}`, { description })
  },

  // Cambiar el estado de un elemento (completado/pendiente)
  toggleItem: (itemId) => {
    return api.patch(`/items/${itemId}/toogle`)
  },

  // Eliminar un elemento
  deleteItem: (itemId) => {
    return api.delete(`/items/${itemId}`)
  },
}

export default {
  folderAPI,
  itemAPI,
}
