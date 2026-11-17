package se.nfctool.app

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.cardemulation.CardEmulation
import android.os.Bundle
import android.content.Intent
import android.graphics.Typeface
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.text.DateFormat
import java.util.Date

class MainActivity : Activity(), NfcAdapter.ReaderCallback {
    private var adapter: NfcAdapter? = null
    private lateinit var output: TextView
    private val prefs by lazy { getSharedPreferences("local_cards", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NfcAdapter.getDefaultAdapter(this)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(36, 42, 36, 24) }
        val heading = TextView(this).apply { text = "MIFARE NFC Tool"; textSize = 25f; setTypeface(null, Typeface.BOLD) }
        root.addView(heading)
        root.addView(TextView(this).apply {
            text = "Håll ett eget NFC-kort mot telefonens baksida. Appen läser endast metadata och kopierar inte passerkort."
            textSize = 15f; setPadding(0, 20, 0, 22)
        })
        val status = if (adapter == null) "NFC saknas" else if (adapter?.isEnabled == true) "NFC aktivt" else "NFC avstängt – aktivera i inställningar"
        val hce = packageManager.hasSystemFeature("android.hardware.nfc.hce")
        root.addView(TextView(this).apply { text = "$status\nAndroid HCE: ${if (hce) "Ja (ISO-DEP, inte MIFARE Classic)" else "Nej"}"; textSize = 16f })
        val save = Button(this).apply { text = "Visa senast sparade kortinfo"; setOnClickListener { output.text = prefs.getString("last", "Inget sparat ännu") } }
        root.addView(save)
        val clear = Button(this).apply { text = "Radera sparad kortinfo"; setOnClickListener { prefs.edit().remove("last").apply(); output.text = "Sparad information raderad." } }
        root.addView(clear)
        output = TextView(this).apply { text = "Väntar på kort…"; textSize = 16f; setTextIsSelectable(true); setPadding(0, 24, 0, 0) }
        root.addView(output)
        setContentView(ScrollView(this).apply { addView(root) })
    }

    override fun onResume() {
        super.onResume()
        adapter?.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, null)
    }

    override fun onPause() {
        adapter?.disableReaderMode(this)
        super.onPause()
    }

    override fun onTagDiscovered(tag: Tag) {
        val uid = tag.id.joinToString("") { "%02X".format(it.toInt() and 0xFF) }
        val techs = tag.techList.joinToString("\n") { it.substringAfterLast('.') }
        val classic = MifareClassic.get(tag)
        val details = if (classic != null) {
            "MIFARE Classic: identifierat\nStorlek: ${classic.size} byte\nSektorer: ${classic.sectorCount}\nBlock: ${classic.blockCount}"
        } else "MIFARE Classic: stöds inte eller kunde inte identifieras av telefonen"
        val report = "Tid: ${DateFormat.getDateTimeInstance().format(Date())}\nUID: $uid\nTekniker:\n$techs\n\n$details\n\nOBS: UID är inte ett emulerat passerkort."
        prefs.edit().putString("last", report).apply()
        runOnUiThread { output.text = report }
    }
}
