package com.example.rastreadorgastos

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DefinirPresupuestoActivity : AppCompatActivity() {

    private var tipoSeleccionado = "Salario mensual"
    private var emojiSeleccionado = "💼"

    private lateinit var opcionSalario: LinearLayout
    private lateinit var opcionGanancias: LinearLayout
    private lateinit var opcionNegocio: LinearLayout
    private lateinit var opcionEmprendimiento: LinearLayout

    private lateinit var checkSalario: TextView
    private lateinit var checkGanancias: TextView
    private lateinit var checkNegocio: TextView
    private lateinit var checkEmprendimiento: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_definir_presupuesto)

        opcionSalario = findViewById(R.id.opcionSalario)
        opcionGanancias = findViewById(R.id.opcionGanancias)
        opcionNegocio = findViewById(R.id.opcionNegocio)
        opcionEmprendimiento = findViewById(R.id.opcionEmprendimiento)

        checkSalario = findViewById(R.id.checkSalario)
        checkGanancias = findViewById(R.id.checkGanancias)
        checkNegocio = findViewById(R.id.checkNegocio)
        checkEmprendimiento = findViewById(R.id.checkEmprendimiento)

        // Actualizar el total del presupuesto
        val listaActual = IngresoStorage.cargar(this)
        val total = listaActual.sumOf { it.valor }
        val formato = NumberFormat.getNumberInstance(Locale("es", "CO"))
        findViewById<TextView>(R.id.totalPresupuestoDefinir).text = "$" + formato.format(total)

        // Seleccionar opción por defecto
        seleccionarOpcion(opcionSalario, checkSalario, "Salario mensual", "💼")

        // Listeners
        opcionSalario.setOnClickListener { seleccionarOpcion(opcionSalario, checkSalario, "Salario mensual", "💼") }
        opcionGanancias.setOnClickListener { seleccionarOpcion(opcionGanancias, checkGanancias, "Ganancias ocasionales", "🎁") }
        opcionNegocio.setOnClickListener { seleccionarOpcion(opcionNegocio, checkNegocio, "Mi negocio", "🏪") }
        opcionEmprendimiento.setOnClickListener { seleccionarOpcion(opcionEmprendimiento, checkEmprendimiento, "Mi emprendimiento", "🚀") }

        // Botón Guardar
        findViewById<Button>(R.id.btnGuardarIngreso).setOnClickListener {
            val valorStr = findViewById<EditText>(R.id.etValorIngreso).text.toString().trim()
            if (valorStr.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa un valor", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val valor = valorStr.toIntOrNull() ?: 0
            if (valor <= 0) {
                Toast.makeText(this, "Por favor ingresa un valor válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear el ingreso
            val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            val nuevoIngreso = Ingreso(tipoSeleccionado, emojiSeleccionado, valor, fecha)

            // Cargar la lista actual, agregar y guardar
            val lista = IngresoStorage.cargar(this)
            lista.add(nuevoIngreso)
            IngresoStorage.guardar(this, lista)

            Toast.makeText(this, "Ingreso guardado: $tipoSeleccionado", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun seleccionarOpcion(
        opcion: LinearLayout,
        check: TextView,
        tipo: String,
        emoji: String
    ) {
        // Quitar selección de todas
        checkSalario.visibility = TextView.INVISIBLE
        checkGanancias.visibility = TextView.INVISIBLE
        checkNegocio.visibility = TextView.INVISIBLE
        checkEmprendimiento.visibility = TextView.INVISIBLE

        opcionSalario.background = getDrawable(R.drawable.bg_card)
        opcionGanancias.background = getDrawable(R.drawable.bg_card)
        opcionNegocio.background = getDrawable(R.drawable.bg_card)
        opcionEmprendimiento.background = getDrawable(R.drawable.bg_card)

        // Marcar la opción seleccionada
        check.visibility = TextView.VISIBLE
        opcion.background = getDrawable(R.drawable.bg_boton_borde_morado)

        // Guardar el tipo y emoji seleccionado
        tipoSeleccionado = tipo
        emojiSeleccionado = emoji
    }
}