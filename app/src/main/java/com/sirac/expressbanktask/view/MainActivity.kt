package com.sirac.expressbanktask.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sirac.expressbanktask.R
import com.sirac.expressbanktask.adapter.*
import com.sirac.expressbanktask.databinding.ActivityMainBinding
import com.sirac.expressbanktask.model.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_bottom_sheet.*

class MainActivity : AppCompatActivity(),
    CategoriesRecyclerAdapter.Listener,
    CardArrayAdapter.Listener,
    YearArrayAdapter.Listener,
    MonthArrayAdapter.Listener,
    CategoryDtRecyclerAdapter.Listener {
    //ArrayList
    private lateinit var chartDataList : ArrayList<PieEntry>
    private lateinit var categoriesList : ArrayList<CategoryModel>
    private lateinit var categoryDtList : ArrayList<CategoryDtModel>
    private lateinit var categoryDtListNew : ArrayList<CategoryDtModel>
    private lateinit var cardsList : ArrayList<CardModel>
    private lateinit var yearsList : ArrayList<YearModel>
    private lateinit var monthsList : ArrayList<MonthModel>
    private lateinit var expencesList : ArrayList<ExpencesModel>
    private lateinit var incomingsList : ArrayList<IncomingsModel>
    private lateinit var cashbacksList : ArrayList<CashbackModel>

    //RecyclerAdapter
    private lateinit var categoryRecyclerAdapter : CategoriesRecyclerAdapter
    private lateinit var categoryDtRecyclerAdapter : CategoryDtRecyclerAdapter

    //BottomSheet
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<RelativeLayout>

    //ViewBinding
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        define()
        chartData()
        categoryData()
        cardData()
        yearData()
        monthData()
        expencesData()
        incomingsData()
        cashbackData()
        categoryDtData()
    }

    //Initialize
    private fun define(){
        chartDataList = ArrayList<PieEntry>()
        categoriesList = ArrayList<CategoryModel>()
        categoryDtList = ArrayList<CategoryDtModel>()
        categoryDtListNew = ArrayList<CategoryDtModel>()
        cardsList = ArrayList<CardModel>()
        yearsList = ArrayList<YearModel>()
        monthsList = ArrayList<MonthModel>()
        expencesList = ArrayList<ExpencesModel>()
        incomingsList = ArrayList<IncomingsModel>()
        cashbacksList = ArrayList<CashbackModel>()
    }

    //Chart
    private fun chartData(){
        val pieData1 = PieEntry(21f, "Airlines")
        val pieData2 = PieEntry(22f, "Rent A Car")
        val pieData3 = PieEntry(23f, "Hotels And Motels")
        val pieData4 = PieEntry(24f, "Transport")
        val pieData5 = PieEntry(25f, "Cars And Vehicles")
        chartDataList.add(pieData1)
        chartDataList.add(pieData2)
        chartDataList.add(pieData3)
        chartDataList.add(pieData4)
        chartDataList.add(pieData5)

        val colors = ArrayList<Int>()
        colors.add(ColorTemplate.rgb("#f1c40f"))
        colors.add(ColorTemplate.rgb("#e67e22"))
        colors.add(ColorTemplate.rgb("#e74c3c"))
        colors.add(ColorTemplate.rgb("#27ae60"))
        colors.add(ColorTemplate.rgb("#2980b9"))

        val pieDataSet = PieDataSet(chartDataList, "Money Management")

        pieDataSet.setColors(colors)
        pieDataSet.selectionShift = 5f
        pieDataSet.sliceSpace = 1f
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.animateXY(4000, 4000)
        pieChart.description.isEnabled = false
        pieChart.centerText = "Express Bank"
        pieChart.animate()
    }

    //Category
    private fun categoryData(){
        categoriesRecycler.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        val category1 = CategoryModel(R.drawable.ic_plane, "Airlines", "21%", "1100 AZN")
        val category2 = CategoryModel(R.drawable.ic_car, "Rent A Car", "22%", "1200 AZN")
        val category3 = CategoryModel(R.drawable.ic_hotel, "Hotels And Motels", "23%", "1300 AZN")
        val category4 = CategoryModel(R.drawable.ic_transport, "Transport", "24%", "1400 AZN")
        val category5 = CategoryModel(R.drawable.ic_vehicle, "Cars And Vehicles", "25%", "1500 AZN")

        categoriesList.add(category1)
        categoriesList.add(category2)
        categoriesList.add(category3)
        categoriesList.add(category4)
        categoriesList.add(category5)

        categoryRecyclerAdapter = CategoriesRecyclerAdapter(categoriesList, this)
        categoriesRecycler.adapter = categoryRecyclerAdapter
        categoryRecyclerAdapter.notifyDataSetChanged()
    }

    override fun onCategoryItemClick(category: CategoryModel) {
        if (cardDropDown.text.toString().equals("Card")){
            Toast.makeText(this@MainActivity, "Please select Card", Toast.LENGTH_SHORT).show()
        }
        if (yearDropDown.text.toString().equals("Year")){
            Toast.makeText(this@MainActivity, "Please select Year", Toast.LENGTH_SHORT).show()
        }
        if (monthDropDown.text.toString().equals("Month")){
            Toast.makeText(this@MainActivity, "Please select Month", Toast.LENGTH_SHORT).show()
        }

        if (!cardDropDown.text.toString().equals("Card") && !yearDropDown.text.toString().equals("Year") && !monthDropDown.text.toString().equals("Month")){
            bottomSheetBehavior = BottomSheetBehavior.from(custom_bottom_sheet)
            bottomSheetBehavior.setPeekHeight(1500)

            categoryNameDt.text = category.name
            categoryExpencesDtTxt.text = "Expences for " + monthDropDown.text.toString() + " " + yearDropDown.text.toString()
            val expencesDt = expencesTxt.text.toString().substring(0, expencesTxt.text.toString().indexOf("\n"))
            categoryCostDtTxt.text = expencesDt + " AZN"
            categoryPercentDtTxt.text = category.percent + " of all"

            categoryDtRecycler.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            categoryDtListNew.clear()
            categoryDtList.forEach {
                if (it.category == category.name){
                    categoryDtListNew.add(it)
                    categoryDtRecyclerAdapter = CategoryDtRecyclerAdapter(categoryDtListNew, this)
                    categoryDtRecycler.adapter = categoryDtRecyclerAdapter
                    categoryDtRecyclerAdapter.notifyDataSetChanged()
                }
            }

            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED){
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }else{
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

            bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED){

                    }else{

                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }
    }

    private fun categoryDtData(){
        val dt1 = CategoryDtModel(R.drawable.ic_plane, "Air Travel", "10:04 01.02.2020", "99 AZN", "Airlines")
        val dt2 = CategoryDtModel(R.drawable.ic_plane, "Air Travel", "15:46 01.02.2020", "199 AZN", "Airlines")
        val dt3 = CategoryDtModel(R.drawable.ic_plane, "Air Travel", "21:38 01.02.2020", "299 AZN", "Airlines")
        val dt4 = CategoryDtModel(R.drawable.ic_car, "New Car Rent", "10:04 01.02.2020", "99 AZN", "Rent A Car")
        val dt5 = CategoryDtModel(R.drawable.ic_car, "New Car Rent", "15:46 01.02.2020", "199 AZN", "Rent A Car")
        val dt6 = CategoryDtModel(R.drawable.ic_car, "New Car Rent", "21:38 01.02.2020", "299 AZN", "Rent A Car")
        val dt7 = CategoryDtModel(R.drawable.ic_hotel, "Hotels Income", "21:38 01.02.2020", "99 AZN", "Hotels And Motels")
        val dt8 = CategoryDtModel(R.drawable.ic_hotel, "Hotels Income", "21:38 01.02.2020", "199 AZN", "Hotels And Motels")
        val dt9 = CategoryDtModel(R.drawable.ic_hotel, "Hotels Income", "21:38 01.02.2020", "299 AZN", "Hotels And Motels")
        val dt10 = CategoryDtModel(R.drawable.ic_transport, "Uber Trip", "21:38 01.02.2020", "99 AZN", "Transport")
        val dt11 = CategoryDtModel(R.drawable.ic_transport, "Uber Trip", "21:38 01.02.2020", "199 AZN", "Transport")
        val dt12 = CategoryDtModel(R.drawable.ic_transport, "Uber Trip", "21:38 01.02.2020", "299 AZN", "Transport")
        val dt13 = CategoryDtModel(R.drawable.ic_vehicle, "Vehicle Renting", "21:38 01.02.2020", "99 AZN", "Cars And Vehicles")
        val dt14 = CategoryDtModel(R.drawable.ic_vehicle, "Vehicle Renting", "21:38 01.02.2020", "199 AZN", "Cars And Vehicles")
        val dt15 = CategoryDtModel(R.drawable.ic_vehicle, "Vehicle Renting", "21:38 01.02.2020", "299 AZN", "Cars And Vehicles")
        categoryDtList.add(dt1)
        categoryDtList.add(dt2)
        categoryDtList.add(dt3)
        categoryDtList.add(dt4)
        categoryDtList.add(dt5)
        categoryDtList.add(dt6)
        categoryDtList.add(dt7)
        categoryDtList.add(dt8)
        categoryDtList.add(dt9)
        categoryDtList.add(dt10)
        categoryDtList.add(dt11)
        categoryDtList.add(dt12)
        categoryDtList.add(dt13)
        categoryDtList.add(dt14)
        categoryDtList.add(dt15)
        categoryDtListNew.addAll(categoryDtList)
    }

    override fun onCategoryDtItemClick(categoryDt: CategoryDtModel) {
        println(categoryDt.category)
    }

    //Card
    private fun cardData(){
        val card1 = CardModel(R.drawable.ic_card, "Express VISA", "****4554")
        val card2 = CardModel(R.drawable.ic_card, "Express MASTER", "****8998")

        cardsList.add(card1)
        cardsList.add(card2)

        val cardArrayAdapter = CardArrayAdapter(this, R.layout.card_dropdown_item, cardsList, this)
        cardDropDown.setAdapter(cardArrayAdapter)
        cardDropDown.freezesText = false
    }

    override fun onCardItemClick(card: CardModel) {
        cardDropDown.dismissDropDown()
        cardDropDown.setText(card.name + " " + card.number, false)

        expencesList.forEach {
            if (cardDropDown.text.toString().contains(it.card) && it.year.equals(yearDropDown.text.toString()) && it.month.equals(monthDropDown.text.toString())){
                expencesTxt.text = it.value + "\n Expences"
            }
        }
        incomingsList.forEach {
            if (cardDropDown.text.toString().contains(it.card) && it.year.equals(yearDropDown.text.toString()) && it.month.equals(monthDropDown.text.toString())){
                incomingsTxt.text = it.value + "\n Incomings"
            }
        }
        cashbacksList.forEach {
            if (cardDropDown.text.toString().contains(it.card) && it.year.equals(yearDropDown.text.toString()) && it.month.equals(monthDropDown.text.toString())){
                cashbackTxt.text = it.value + "\n Cashback"
            }
        }
    }

    //Year
    private fun yearData(){
        val year1 = YearModel("2020")
        val year2 = YearModel("2021")
        val year3 = YearModel("2022")

        yearsList.add(year1)
        yearsList.add(year2)
        yearsList.add(year3)

        val yearArrayAdapter = YearArrayAdapter(this, R.layout.year_dropdown_item, yearsList, this)
        yearDropDown.setAdapter(yearArrayAdapter)
    }

    override fun onYearItemClick(year: YearModel) {
        yearDropDown.dismissDropDown()
        yearDropDown.setText(year.year, false)

        expencesList.forEach {
            if (cardDropDown.text.toString().contains(it.card) && it.year.equals(yearDropDown.text.toString()) && it.month.equals(monthDropDown.text.toString())){
                expencesTxt.text = it.value + "\n Expences"
            }
        }
        incomingsList.forEach {
            if (cardDropDown.text.toString().contains(it.card) && it.year.equals(yearDropDown.text.toString()) && it.month.equals(monthDropDown.text.toString())){
                incomingsTxt.text = it.value + "\n Incomings"
            }
        }
        cashbacksList.forEach {
            if (cardDropDown.text.toString().contains(it.card) && it.year.equals(yearDropDown.text.toString()) && it.month.equals(monthDropDown.text.toString())){
                cashbackTxt.text = it.value + "\n Cashback"
            }
        }
    }

    //Month
    private fun monthData(){
        val month1 = MonthModel("January")
        val month2 = MonthModel("February")
        val month3 = MonthModel("March")

        monthsList.add(month1)
        monthsList.add(month2)
        monthsList.add(month3)

        val monthArrayAdapter = MonthArrayAdapter(this,
            R.layout.month_dropdown_item, monthsList, this)
        monthDropDown.setAdapter(monthArrayAdapter)
    }

    override fun onMonthItemClick(month: MonthModel) {
        monthDropDown.dismissDropDown()
        monthDropDown.setText(month.month, false)

        expencesList.forEach {
            if (cardDropDown.text.toString().contains(it.card) && it.year.equals(yearDropDown.text.toString()) && it.month.equals(monthDropDown.text.toString())){
                expencesTxt.text = it.value + "\n Expences"
            }
        }
        incomingsList.forEach {
            if (cardDropDown.text.toString().contains(it.card) && it.year.equals(yearDropDown.text.toString()) && it.month.equals(monthDropDown.text.toString())){
                incomingsTxt.text = it.value + "\n Incomings"
            }
        }
        cashbacksList.forEach {
            if (cardDropDown.text.toString().contains(it.card) && it.year.equals(yearDropDown.text.toString()) && it.month.equals(monthDropDown.text.toString())){
                cashbackTxt.text = it.value + "\n Cashback"
            }
        }
    }

    //Expences
    private fun expencesData(){
        val data1 = ExpencesModel("****4554", "2020", "January", "1100000")
        val data2 = ExpencesModel("****4554", "2020", "February", "1200000")
        val data3 = ExpencesModel("****4554", "2020", "March", "1300000")
        val data4 = ExpencesModel("****4554", "2021", "January", "1400000")
        val data5 = ExpencesModel("****4554", "2021", "February", "1500000")
        val data6 = ExpencesModel("****4554", "2021", "March", "1600000")
        val data7 = ExpencesModel("****4554", "2022", "January", "1700000")
        val data8 = ExpencesModel("****4554", "2022", "February", "1800000")
        val data9 = ExpencesModel("****4554", "2022", "March", "1900000")
        val data10 = ExpencesModel("****8998", "2020", "January", "2100000")
        val data11 = ExpencesModel("****8998", "2020", "February", "2200000")
        val data12 = ExpencesModel("****8998", "2020", "March", "2300000")
        val data13 = ExpencesModel("****8998", "2021", "January", "2400000")
        val data14 = ExpencesModel("****8998", "2021", "February", "2500000")
        val data15 = ExpencesModel("****8998", "2021", "March", "2600000")
        val data16 = ExpencesModel("****8998", "2022", "January", "2700000")
        val data17 = ExpencesModel("****8998", "2022", "February", "2800000")
        val data18 = ExpencesModel("****8998", "2022", "March", "2900000")

        expencesList.add(data1)
        expencesList.add(data2)
        expencesList.add(data3)
        expencesList.add(data4)
        expencesList.add(data5)
        expencesList.add(data6)
        expencesList.add(data7)
        expencesList.add(data8)
        expencesList.add(data9)
        expencesList.add(data10)
        expencesList.add(data11)
        expencesList.add(data12)
        expencesList.add(data13)
        expencesList.add(data14)
        expencesList.add(data15)
        expencesList.add(data16)
        expencesList.add(data17)
        expencesList.add(data18)
    }

    //Incomings
    private fun incomingsData(){
        val data1 = IncomingsModel("****4554", "2020", "January", "1101100")
        val data2 = IncomingsModel("****4554", "2020", "February", "1201200")
        val data3 = IncomingsModel("****4554", "2020", "March", "1301300")
        val data4 = IncomingsModel("****4554", "2021", "January", "1401400")
        val data5 = IncomingsModel("****4554", "2021", "February", "1501500")
        val data6 = IncomingsModel("****4554", "2021", "March", "1601600")
        val data7 = IncomingsModel("****4554", "2022", "January", "1701700")
        val data8 = IncomingsModel("****4554", "2022", "February", "1801800")
        val data9 = IncomingsModel("****4554", "2022", "March", "1901900")
        val data10 = IncomingsModel("****8998", "2020", "January", "2102100")
        val data11 = IncomingsModel("****8998", "2020", "February", "2202200")
        val data12 = IncomingsModel("****8998", "2020", "March", "2302300")
        val data13 = IncomingsModel("****8998", "2021", "January", "2402400")
        val data14 = IncomingsModel("****8998", "2021", "February", "2502500")
        val data15 = IncomingsModel("****8998", "2021", "March", "2602600")
        val data16 = IncomingsModel("****8998", "2022", "January", "2702700")
        val data17 = IncomingsModel("****8998", "2022", "February", "2802800")
        val data18 = IncomingsModel("****8998", "2022", "March", "2902900")

        incomingsList.add(data1)
        incomingsList.add(data2)
        incomingsList.add(data3)
        incomingsList.add(data4)
        incomingsList.add(data5)
        incomingsList.add(data6)
        incomingsList.add(data7)
        incomingsList.add(data8)
        incomingsList.add(data9)
        incomingsList.add(data10)
        incomingsList.add(data11)
        incomingsList.add(data12)
        incomingsList.add(data13)
        incomingsList.add(data14)
        incomingsList.add(data15)
        incomingsList.add(data16)
        incomingsList.add(data17)
        incomingsList.add(data18)
    }

    //Cashback
    private fun cashbackData(){
        val data1 = CashbackModel("****4554", "2020", "January", "1100011")
        val data2 = CashbackModel("****4554", "2020", "February", "1200012")
        val data3 = CashbackModel("****4554", "2020", "March", "1300013")
        val data4 = CashbackModel("****4554", "2021", "January", "1400014")
        val data5 = CashbackModel("****4554", "2021", "February", "1500015")
        val data6 = CashbackModel("****4554", "2021", "March", "1600016")
        val data7 = CashbackModel("****4554", "2022", "January", "1700017")
        val data8 = CashbackModel("****4554", "2022", "February", "1800018")
        val data9 = CashbackModel("****4554", "2022", "March", "1900019")
        val data10 = CashbackModel("****8998", "2020", "January", "2100021")
        val data11 = CashbackModel("****8998", "2020", "February", "2200022")
        val data12 = CashbackModel("****8998", "2020", "March", "2300023")
        val data13 = CashbackModel("****8998", "2021", "January", "2400024")
        val data14 = CashbackModel("****8998", "2021", "February", "2500025")
        val data15 = CashbackModel("****8998", "2021", "March", "2600026")
        val data16 = CashbackModel("****8998", "2022", "January", "2700027")
        val data17 = CashbackModel("****8998", "2022", "February", "2800028")
        val data18 = CashbackModel("****8998", "2022", "March", "2900029")

        cashbacksList.add(data1)
        cashbacksList.add(data2)
        cashbacksList.add(data3)
        cashbacksList.add(data4)
        cashbacksList.add(data5)
        cashbacksList.add(data6)
        cashbacksList.add(data7)
        cashbacksList.add(data8)
        cashbacksList.add(data9)
        cashbacksList.add(data10)
        cashbacksList.add(data11)
        cashbacksList.add(data12)
        cashbacksList.add(data13)
        cashbacksList.add(data14)
        cashbacksList.add(data15)
        cashbacksList.add(data16)
        cashbacksList.add(data17)
        cashbacksList.add(data18)
    }
}
