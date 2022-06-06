package com.mertoenjosh.tabiandating.util

import java.io.File


object FileSearch {
    private const val TAG = "FileSearch"

    /**
     * Search a directory and return a list of all **directories** contained inside
     * @param directory
     * @return
     */
    fun getDirectoryPaths(directory: String?): ArrayList<String> {
        val pathArray = ArrayList<String>()
        var file: File? = null
        var listfiles: Array<File>? = null
        try {
            file = File(directory)
            listfiles = file.listFiles()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            for (i in listfiles!!.indices) {
                if (listfiles[i].isDirectory) {
                    pathArray.add(listfiles[i].absolutePath)
                }
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return pathArray
    }

    /**
     * Search a directory and return a list of all **files** contained inside
     * @param directory
     * @return
     */
    fun getFilePaths(directory: String?): ArrayList<String> {
        val pathArray = ArrayList<String>()
        var file: File? = null
        var listfiles: Array<File>? = null
        try {
            file = File(directory)
            listfiles = file.listFiles()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            for (i in listfiles!!.indices) {
                if (listfiles[i].isFile) {
                    pathArray.add(listfiles[i].absolutePath)
                }
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return pathArray
    }
}












