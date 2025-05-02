"use client"

import { useState } from "react"

function FolderForm({ folder, onSubmit, onCancel }) {
  const [name, setName] = useState(folder ? folder.name : "")
  const [error, setError] = useState("")

  const handleSubmit = (e) => {
    e.preventDefault()

    if (!name.trim()) {
      setError("El nombre no puede estar vacío")
      return
    }

    onSubmit(name)
    if (!folder) {
      setName("")
    }
    setError("")
  }

  return (
    <form onSubmit={handleSubmit}>
      <div className="input-group mb-3">
        <input
          type="text"
          className={`form-control ${error ? "is-invalid" : ""}`}
          placeholder="Nombre de la carpeta"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <button className="btn btn-primary" type="submit">
          {folder ? "Actualizar" : "Crear"}
        </button>
        {folder && (
          <button className="btn btn-secondary" type="button" onClick={onCancel}>
            Cancelar
          </button>
        )}
      </div>
      {error && <div className="invalid-feedback d-block">{error}</div>}
    </form>
  )
}

export default FolderForm
