"use client"

import { useState } from "react"

function ItemForm({ item, onSubmit, onCancel }) {
  const [description, setDescription] = useState(item ? item.description : "")
  const [error, setError] = useState("")

  const handleSubmit = (e) => {
    e.preventDefault()

    if (!description.trim()) {
      setError("La descripción no puede estar vacía")
      return
    }

    onSubmit(description)
    if (!item) {
      setDescription("")
    }
    setError("")
  }

  return (
    <form onSubmit={handleSubmit}>
      <div className="input-group mb-3">
        <input
          type="text"
          className={`form-control ${error ? "is-invalid" : ""}`}
          placeholder="Descripción del elemento"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
        <button className="btn btn-primary" type="submit">
          {item ? "Actualizar" : "Crear"}
        </button>
        {item && (
          <button className="btn btn-secondary" type="button" onClick={onCancel}>
            Cancelar
          </button>
        )}
      </div>
      {error && <div className="invalid-feedback d-block">{error}</div>}
    </form>
  )
}

export default ItemForm
