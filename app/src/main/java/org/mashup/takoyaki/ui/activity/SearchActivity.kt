package org.mashup.takoyaki.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.text.Selection
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_search.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.R.id.svTruck

class SearchActivity : AppCompatActivity() {

    companion object {
        private val TAG = SearchActivity::class.java.simpleName

        fun start(context: Context) {
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.activity_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        svTruck.isIconified = false
        svTruck.setIconifiedByDefault(false)

        svTruck.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private val TAKOUAKI = "타코야키"
            private var isAddTakoyaki = false

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return false
                }
                Log.d(TAG, "submit query $query")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (!newText.contains(TAKOUAKI) && !isAddTakoyaki) {
                    svTruck.setQuery("$newText $TAKOUAKI", false)
                    isAddTakoyaki = true
                    val editView = svTruck.findViewById<EditText>(R.id.search_src_text)
                    Selection.setSelection(editView.editableText, newText.length)
                }

                if (newText.isEmpty()) {
                    isAddTakoyaki = false
                }

                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
