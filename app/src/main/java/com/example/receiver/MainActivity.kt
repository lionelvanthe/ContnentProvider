package com.example.receiver

import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.app.LoaderManager
import androidx.loader.app.LoaderManager.LoaderCallbacks
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.example.receiver.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), LoaderCallbacks<Cursor> {

    private lateinit var broadCast: BroadCast
    private lateinit var binding: ActivityMainBinding
    private var listContact = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadCast = BroadCast()
        val filter = IntentFilter("test.Broadcast")
        registerReceiver(broadCast, filter)



        binding.tv.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS), 0)

            }
            else{
                LoaderManager.getInstance(this).initLoader(1, null, this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadCast)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        return CursorLoader(
            this, uri, null,
            null, null, null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        data?.let{
            if(it.count > 0){
                while(it.moveToNext()){
                    val number = it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val name = it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    listContact.add(Contact(name, number))
                }
            }
        }

        val adapter = ContactsAdapter(listContact, this)
        binding.recyclerViewContact.adapter = adapter
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    LoaderManager.getInstance(this).initLoader(1, null, this)
                }
            }
        }
    }
}