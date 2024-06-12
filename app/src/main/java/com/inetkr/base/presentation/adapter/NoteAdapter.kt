package com.inetkr.base.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inetkr.base.databinding.HolderNoteBinding
import com.inetkr.base.domain.entity.response.Note
import com.inetkr.base.presentation.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.inetkr.base.utils.extensions.onAvoidDoubleClick


class NoteAdapter(context: Context) :
    EndlessLoadingRecyclerViewAdapter(context, false) {

    var onClickItem: (Note?) -> Unit = {}

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = HolderNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val item = getItem(position, Note::class.java)
        item?.let {
            (holder as? NoteViewHolder)?.bind(item)
        }
    }

    inner class NoteViewHolder(private val binding: HolderNoteBinding) :
        NormalViewHolder(binding.root) {
        init {
            itemView.onAvoidDoubleClick {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(bindingAdapterPosition, Note::class.java)
                    onClickItem(item)
                }
            }
        }

        fun bind(item: Note) {
            binding.apply {
                tvName.text = item.title
            }
        }
    }
}