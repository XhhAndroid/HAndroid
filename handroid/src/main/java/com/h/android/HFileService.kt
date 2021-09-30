package com.h.android

import android.Manifest
import android.content.Context
import android.os.Environment
import androidx.annotation.RequiresPermission
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.io.*
import java.util.*

/**
 *2021/9/10
 *@author zhangxiaohui
 *@describe 文件管理
 */
class HFileService private constructor() {

    companion object {
        private var fileService: HFileService? = null

        @Synchronized
        fun get(): HFileService {
            if (fileService == null) {
                fileService = HFileService()
            }
            return fileService!!
        }
    }

    /**
     * 获取私有文件内容
     */
    fun getPrivateFileContent(fileName: String): Observable<String> {
        return getPrivateFileDir()
            .flatMap { file ->
                readFileString(file, fileName)
            }
    }

    /**
     * 公共存储空间文件内容
     */
    @RequiresPermission(allOf = [Manifest.permission.WRITE_EXTERNAL_STORAGE])
    fun getPublicFileContent(fileName: String): Observable<String> {
        return getPublicFileDir()
            .flatMap { file ->
                readFileString(file, fileName)
            }
    }

    /**
     * 存储内容到私有文件
     * @param append 追加文件内容
     */
    fun savePrivateFile(fileName: String, content: String, append: Boolean): Observable<File> {
        return getPrivateFileDir()
            .flatMap { file ->
                writeFileString(file, fileName, content, append)
            }
    }

    /**
     * 存储内容到公共文件
     * @param append 追加文件内容
     */
    @RequiresPermission(allOf = [Manifest.permission.WRITE_EXTERNAL_STORAGE])
    fun savePublicFile(fileName: String, content: String, append: Boolean): Observable<File> {
        return getPublicFileDir()
            .flatMap { file ->
                writeFileString(file, fileName, content, append)
            }
    }

    /**
     * 获取私有存储目录
     */
    fun getPrivateFileDir(): Observable<File> {
        return Observable.fromCallable {
            val file: File = HAndroid.getApplication().getDir(
                HFileService::class.java.simpleName,
                Context.MODE_PRIVATE
            )
            if (!file.exists()) {
                file.mkdirs()
            }
            file
        }.subscribeOn(Schedulers.io())
    }

    /**
     * 获取公共存储空间目录
     */
    @RequiresPermission(allOf = [Manifest.permission.WRITE_EXTERNAL_STORAGE])
    fun getPublicFileDir(): Observable<File> {
        return Observable.fromCallable {
            val file = File(
                StringBuilder(Environment.getExternalStorageDirectory().absolutePath)
                    .append(File.separator)
                    .append(HAndroid.getApplication().packageName)
                    .append(File.separator)
                    .append(HFileService::class.java.simpleName)
                    .toString()
            )
            if (!file.exists()) {
                file.mkdirs()
            }
            file
        }.subscribeOn(Schedulers.io())
    }

    fun saveInputStreamToPrivateFile(fileName: String, inputStream: InputStream): Observable<File> {
        return getPrivateFileDir()
            .flatMap { file ->
                val file = File(file, fileName)
                if (!file.parentFile.exists()) {
                    file.parentFile.mkdirs()
                }
                if (!file.exists()) {
                    file.createNewFile()
                }

                val fileOutputStream = FileOutputStream(file)
                val bytes: ByteArray = ByteArray(1024)
                var readLength = 0
                var curLength = 0
                while (readLength != -1) {
                    fileOutputStream.write(bytes, 0, readLength)
                    curLength += readLength
                    readLength = inputStream.read(bytes)
                }
                inputStream.close()
                fileOutputStream.close()
                return@flatMap Observable.just(file)
            }
    }
}

private fun writeFileString(dir: File, fileName: String, content: String, append: Boolean): Observable<File> {
    return Observable
        .fromCallable {
            val file = File(dir, fileName)
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            if (!file.exists()) {
                file.createNewFile()
            }
            file
        }.map { file ->
            FileWriter(file, append).use { fw ->
                fw.write(content)
                fw.flush()
            }
            file
        }
}

private fun readFileString(dir: File, fileName: String): Observable<String> {
    return Observable.fromCallable {
        val file = File(dir, fileName)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        if (!file.exists()) {
            file.createNewFile()
        }
        file
    }.map(Function { file ->
        FileReader(file).use { fr ->
            val bt = CharArray(1024)
            val sb = StringBuffer()
            while (fr.read(bt) != -1) {
                sb.append(bt)
                Arrays.fill(bt, 0.toChar())
            }
            return@Function sb.toString()
        }
    })
}