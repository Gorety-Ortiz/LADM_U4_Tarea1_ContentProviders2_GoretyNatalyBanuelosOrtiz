package mx.tecnm.tepic.ladm_u4_tarea1_contentproviders2

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val siPermiso = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_CALL_LOG),siPermiso)
        }

        button.setOnClickListener {
            leerRegistroLlamadas()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==siPermiso){
            //al mandar el permiso de forma imediata llama el metodo
            setTitle("PERMISO OTORGADO")
        }
    }
    private fun leerRegistroLlamadas() {
        var resultado = ""
        val cursor = contentResolver.query(
            Uri.parse("content://call_log/calls"),
            null, null, null, null
        )

        if(cursor!!.moveToFirst()){
            do {
                var contacto = cursor.getString(
                    cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
                )
                var telefono = cursor.getString(
                    cursor.getColumnIndex(CallLog.Calls.NUMBER)
                )
                var fecha = cursor.getString(
                    cursor.getColumnIndex(CallLog.Calls.DATE)
                )
                var nombre = contacto
                if(nombre == editText.text.toString()) {
                    resultado += "Contacto: "+contacto+ "\nTelefono: " +telefono+ "\n------------\n"
                }

            }while (cursor.moveToNext())
            textView2.setText(resultado)
        }else{
            resultado= "NO HAY LLAMADAS CON ESTE CONTACTO"

        }
    }
}