package io.embrace.android.embracesdk.comms.delivery

import io.embrace.android.embracesdk.payload.SessionMessage

/**
 * Handles the caching of objects.
 */
internal interface CacheService {
    /**
     * Caches the specified object.
     *
     * @param name   the name of the object to cache
     * @param objectToCache the object to cache
     * @param clazz  the class of the object to cache
     * @param <T>    the type of the object
     */
    fun <T> cacheObject(name: String, objectToCache: T, clazz: Class<T>)

    /**
     * Reads the specified object from the cache, if it exists.
     *
     * @param name  the name of the object to read from the cache
     * @param clazz the class of the cached object
     * @param <T>   the type of the cached object
     * @return optionally the object, if it can be read successfully
     */
    fun <T> loadObject(name: String, clazz: Class<T>): T?

    /**
     * Caches a byte array to disk.
     *
     * @param name   the name of this cache in disk
     * @param bytes  the bytes to write
     */
    fun cacheBytes(name: String, bytes: ByteArray?)

    /**
     * Serializes a session object to disk via a stream. This saves memory when the session is
     * large & the return value isn't used (e.g. for a crash & periodic caching)
     */
    fun writeSession(name: String, sessionMessage: SessionMessage)

    /**
     * Reads the bytes from a cached file, if it exists.
     *
     * @param name  the name of the file to read
     * @return the byte array, if it can be read successfully
     */
    fun loadBytes(name: String): ByteArray?

    /**
     * Delete a file from the cache
     *
     * @param name  the name of the file to delete
     */
    fun deleteFile(name: String): Boolean

    /**
     * Deletes the specified object from the cache.
     *
     * @param name the name of the object to delete
     * @return true if the file was successfully deleted, false otherwise
     */
    fun deleteObject(name: String): Boolean

    /**
     * Deletes the objects which names match with the specified regex from the cache.
     *
     * @param regex the regex to match to the name of the object to delete
     * @return true if the files were successfully deleted, false otherwise
     */
    fun deleteObjectsByRegex(regex: String): Boolean

    /**
     * Moves the object using the current name to a new file called name.
     *
     * @param src the source file name
     * @param dst the destination file name
     * @return true if the file was successfully moved, false otherwise
     */
    fun moveObject(src: String, dst: String): Boolean

    /**
     * Get file names in cache that start with a given prefix.
     *
     * @param prefix    start of the file names to look for
     * @return list of file names
     */
    fun listFilenamesByPrefix(prefix: String): List<String>?
}
