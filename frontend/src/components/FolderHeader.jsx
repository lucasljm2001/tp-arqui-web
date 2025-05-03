"use client"
import FolderForm from "./FolderForm"

function FolderHeader({ folder, isEditing, onEdit, onUpdate, onCancelEdit }) {
  return (
    <div className="d-flex justify-content-between align-items-center mb-4">
      {isEditing ? (
        <FolderForm folder={folder} onSubmit={onUpdate} onCancel={onCancelEdit} />
      ) : (
        <div className="d-flex align-items-center">
          <h2 className="me-2">{folder?.name}</h2>
          <button className="btn btn-outline-secondary" onClick={onEdit}>
            <i className="bi bi-pencil me-1"></i>
            Editar
          </button>
        </div>
      )}
    </div>
  )
}

export default FolderHeader
