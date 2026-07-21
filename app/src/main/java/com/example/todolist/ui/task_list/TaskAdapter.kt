package com.example.todolist.ui.task_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.domain.model.TaskModel

/**
 * Adapter para exibir a lista de tarefas no RecyclerView.
 * Utiliza ListAdapter e DiffUtil para atualizações performáticas.
 */
class TaskAdapter(
    private val onTaskChecked: (Int, Boolean) -> Unit,
    private val onDeleteClicked: (TaskModel) -> Unit
) : ListAdapter<TaskModel, TaskAdapter.TaskViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskModel) {
            binding.apply {
                tvTitle.text = task.title
                tvDescription.text = task.description
                cbCompleted.isChecked = task.isCompleted

                // Evita disparo do listener durante o bind
                cbCompleted.setOnCheckedChangeListener(null)
                cbCompleted.isChecked = task.isCompleted
                cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                    onTaskChecked(task.id, isChecked)
                }

                btnDelete.setOnClickListener {
                    onDeleteClicked(task)
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TaskModel>() {
        override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
            return oldItem == newItem
        }
    }
}
