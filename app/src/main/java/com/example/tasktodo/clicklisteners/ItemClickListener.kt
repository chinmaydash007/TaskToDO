package com.example.tasktodo.clicklisteners

import com.example.tasktodo.db.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)
}