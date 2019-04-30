package com.example.meigel.sispak.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.helpers.SQLiteHelper;
import com.example.meigel.sispak.helpers.SessionHelper;
import com.example.meigel.sispak.models.Gejala;
import com.example.meigel.sispak.models.Keputusan;
import com.example.meigel.sispak.models.Penyakit;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(SessionHelper.getInstance(this).getAppFirstTime()){
            Log.d("MainApp","First session");
            queryGejala();
            queryKeputusan();
            queryPenyakit();
            SessionHelper.getInstance(this).setAppFirstTime(false);
        }
        else {
            Log.d("MainApp","Not First session");
//            SQLiteHelper.getInstance(this).onUpdate();
//            queryPenyakit();
        }
        CheckUpdate();
    }

    private void queryDelete() {
//            SQLiteHelper.getInstance(this).onUpdate();

    }

    private static String url = "https://kliniksip.firebaseio.com/Version/Lastest/apkInfo.json";

    private void CheckUpdate() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                com.example.meigel.sispak.views.activities.Handler handler =
                        new com.example.meigel.sispak.views.activities.Handler();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                Log.d("Fail",e.getMessage());
                                String input = "{" +
                                        "\"path\":\"https://drive.google.com/drive/folders/1vcUtYW8YQ1uus_ZF9fsA_md6tAd8VTkA\"," +
                                        "\"versionCode\":1," +
                                        "\"versionName\":\"Dasar\"}";
                                Intent i = new Intent(SplashScreenActivity.this, MenuActivity.class);
                                i.putExtra("Rest",input);
                                startActivity(i);
                                finish();

                            }
                            @Override
                            public void onResponse(Response response) throws IOException {
                                String resp = response.body().string();
                                System.out.println("resp = " + resp);
                                Intent i = new Intent(SplashScreenActivity.this, MenuActivity.class);
                                i.putExtra("Rest",resp);
                                startActivity(i);
                                finish();
                            }
                        });



            }
        }, 1000);


    }

    private void queryPenyakit(){

        SQLiteHelper
                .getInstance(this)
                .addPenyakit(
                        new Penyakit(
                                "H1",
                                "Penyakit Hog Cholera",
                                "Vaksinasi dengan serum anti cholera babi atau rovac hog cholera.Sesudah babi umur 6" +
                                        "minggu, diulangi setahun sekali.Babi-babi dara atau induk sebaiknya 3 minggu sebelum" +
                                        "dikawinkan, sedang pejantan bisa sewaktu-waktu",
                                "Hog cholera (HC) merupakan penyakit viral menular terpenting pada babi," +
                                        "berlangsung subakut, akut atau kronik, dengan proses penyakit yang tidak" +
                                        "menciri atau bahkan kadang tidak tampak sama sekali.",
                                "pig",
                                "Penyebab hog cholera adalah virus single stranded Ribonucleic Acid (ss-" +
                                        "RNA) dari genus Pestivirus termasuk famili Flaviviridae. Virus HC berada dalam" +
                                        "genus yang sama dengan virus bovine viral diarrhea (BVD). Virus berbentuk bulat" +
                                        "helikal atau tidak teratur dan berukuran antara 40-50 nm dengan nukleokapsid" +
                                        "berukuran 29 nm.",
                                "Belum ada obat yang efektif untuk mencegah hog cholera.",
                                "Tindakan yang paling efektif untuk mencegah atau mengendalikan" +
                                        "penyakit adalah melakukan vaksinasi dengan menggunakan vaksin"+
                                        "aktif yang sudah diatenuasi. Keberhasilan program vaksinasi sangat" +
                                        "tergantung dari strain, dosis dan aplikasi vaksin serta status kesehatan" +
                                        "hewan yang divaksinasi. Pengendalian dapat dilakukan dengan melalui" +
                                        "tindakan karantina."
                        ));
        SQLiteHelper
                .getInstance(this)
                .addPenyakit(new Penyakit(
                        "H2",
                        "Penyakit Cacar Babi",
                        "Makanan diberi TM 10. Berikan penstrep, terramycin injeksi, ditambah vitamin A.",
                        "Penyakit Cacar Babi merupakan penyakit menular pada babi, ditandai dengan " +
                                "adanya Iepuh dan keropeng pada kulit. Cacar babi biasanya merupakan " +
                                "penyakit ringan dengan lesi terbatas pada kulit. Anak babi terserang dengan tingkat mortalitas tinggi.",
                        "imgicon",
                        "Cacar babi disebabkan oleh Swinepoxvirus dari famili Poxviridae. Semua strain" +
                                "memiliki imunogenik yang sama dan dapat dibedakan dengan virus pox lainnya." +
                                "Genom virus tersusun atas DNA beruntai tunggal (ss-DNA) dengan berat molekul" +
                                "80x10 6 D. Asam inti tersebut dilapisi protein (nukleoid) dan lapisan luar terdapat" +
                                "membran serta di kedua sisi terdapat badan lateral bentuknya elips. Partikel virus" +
                                "ini dibungkus oleh struktur membran (amplop). Bentuk virus seperti cakram atau" +
                                "bundar dan berukuran 300 -350 nm x 200- 250 nm.",
                        "Pengobatan spesifik terhadap cacar babi belum ada. Pengobatan" +
                                "dengan yodium pekat dan antibiotika pada kulit dapat mencegah infeksi" +
                                "sekunder.",
                        "Pemeliharaan yang baik, serta kebersihan akan menolong keselamatan babi, terhindar" +
                                "dari penyakit tersebut."
                ));
        SQLiteHelper
                .getInstance(this)
                .addPenyakit(new Penyakit(
                        "H3",
                        "Penyakit Colibasilosis",
                        "Kandang diusahakan selalalu kering dan hangat, latai diberi alas dan sering diganti, " +
                                "tidak sampai mejadi kotor ataupun basah aibat air kencing. Makanan diberi tambahan aureomycin",
                        "Colibacillosis adalah penyakit pada hewan, terutama yang berumur muda yang" +
                                "disebabkan oleh bakteri Escherichia coli (E.coli).",
                        "imgicon",
                        "E.coli penyebab colibacillosis adalah bakteri berbentuk batang berukuran" +
                                "0,5x1,0-3,0 mikrometer, Gram negatif, motif dan tidak membentuk spora. Bakteri" +
                                "ini tidak selalu berbentuk, melainkan dapat dijumpai dengan bentuk coccoid" +
                                "bipolar hingga fi lamen. Kedudukan sel bakteri satu dengan yang lain lazimnya" +
                                "sendiri-sendiri, tetapi dapat pula merupakan rantai pendek (short chains). Skema" +
                                "antigenik genus didasarkan pada adanya bermacam tipe antigen yaitu O, H dan" +
                                "K. Yang terakhir ini dibagi ke dalam antigen L, A dan B.",
                        "Bermacam-macam antibiotik diketahui memberikan hasil baik terhadap" +
                                "kejadian colibacillosis, diantaranya tetracycline, neomycin dan streptomycin." +
                                "Kebiasaan memberikan antibiotik kepada anak ternak sering menimbulkan" +
                                "resistensi. Pemberian antibiotik pada ternak potong dihentikan sekurang-" +
                                "kurangnya 7 hari sebelum dipotong. Selain pemberian antibiotik atau" +
                                "sulfonamide, obat-obatan penunjang lainnya, sebaiknya diberikan juga infus" +
                                "dengan NaCl fi siologis.",
                        "Hindari keadaan penuh sesak di kandang (over crowded), usahakan" +
                                "ternak terbagi dalam kelompok kecil dan terdiri dari umur yang sama." +
                                "Pengendalian colibacillosis pada anak ternak adalah dengan manajemen" +
                                "kandang dan hygiene yang baik. Lantai kandang terbuat dari bahan yang" +
                                "mudah dibersihkan. Disinfektan kandang dilakukan setiap ada pergantian" +
                                "kelompok ternak. Tempat pakan dan air minum diletakkan sedemikian rupa" +
                                "sehingga terhindar dari pencemaran feses."
                ));
        SQLiteHelper
                .getInstance(this)
                .addPenyakit(new Penyakit(
                        "H4",
                        "Penyakit Streptococosis",
                        "",
                        "Streptococcosis pada babi dalam hal ini dibatasi hanya pada penyakit yang disebabkan oleh Streptococcus sp. " +
                                "yang ditandai dengan adanya poliartritis, septikemia dan meningitis. " +
                                "Penyakit ini menimbulkan kerugian berupa kematian, baik pada anak babi maupun babi dewasa, " +
                                "biaya pengobatan yang tinggi dan bersifat zoonotik. Pada manusia, " +
                                "Streptococcus suis dapat menimbulkan septikemia, meningitis dan endokarditis.",
                        "imgicon",
                        "Penyebab streptococcosis pada babi adalah Streptococcus equi subspesies" +
                                "zooepidemicus (Str.zooepidemicus) dan Streptoccocus suis (Str.suis) tipe 2." +
                                "Str.zooepidemicus termasuk dalam grup Lancefi eld’s C, sedangkan Str.suis" +
                                "termasuk dalam grup Lancefi eld’s D.",
                        "Pengobatan yang paling efektif adalah dengan preparat penisilin." +
                                "Disamping itu oxytetracyclin dan kanamycin juga cukup efektif untuk" +
                                "pengobatan streptococcosis pada babi.",
                        "pencegahan dapat dilakukan dengan menjaga kebersihan kandang, tempat pakan dan minuman. " +
                                "Pemberian pakan berasal dari Iimbah hewan sakit harus dihindari."
                ));
        SQLiteHelper
                .getInstance(this)
                .addPenyakit(new Penyakit(
                        "H5",
                        "Penyakit Erysipelas",
                        "",
                        "Erysipelas adalah penyakit hewan menular yang disebabkan oleh bakterial dan terutama menyerang babi. " +
                                "Bakteri Erysipelothrix rusiopathiae penyebab penyakit tersebut berkesan seperti berlian atau " +
                                "Erysipelathrix insidiosa. Erysipelas pada babi biasanya menimbulkan " +
                                "bercak-bercak merah pada kulit sehingga sering disebut “diamond skin disease”. ",
                        "imgicon",
                        "E.insidiosa berbentuk batang langsing, kecil, lurus, membentuk kurva atau" +
                                "membentuk fi lamen, membentuk koloni halus (S), koloni kasar (R) dan koloni" +
                                "antara halus dan kasar (RS) tergantung type dari erysipelas. Bakteri yang" +
                                "berasal dari koloni halus berbentuk batang halus, panjang 0,5-2,5 micrometer" +
                                "sedangkan yang berasal koloni kasar membentuk fi lamen, bercabang atau" +
                                "membentuk lingkaran, dengan ukuran panjang antara 4,15 micrometer. Bakteri ini" +
                                "tidak membentuk spora, tidak bergerak dan bersifat Gram positif, biasa terdapat" +
                                "pada tonsil dan selaput lendir pada babi sehat dan hewan lain.",
                        "Serum kebal, digunakan untuk tujuan profi laksis dan pengobatan." +
                                "Diberikan 5 ml serum untuk hewan yang beratnya sama" +
                                "dengan atau kurang dari 50 kg. Untuk tiap kenaikan berat" +
                                "badan 10 kg di atas 50 kg diberikan tambahan 1 ml serum" +
                                "Imunitas yang ditimbulkan tidak lebih dari 15 hari.",
                        "Vaksin" +
                                "Attenuated vaccine: atenuasi dilakukan pada kelinci. Kekebalan" +
                                "yang ditimbulkan 8-12 bulan. Untuk ternak bibit dilakukan vaksinasi" +
                                "ulang selang 1 tahun."
                ));
        SQLiteHelper
                .getInstance(this)
                .addPenyakit(new Penyakit(
                        "H6",
                        "Penyakit Koksidiosis",
                        "",
                        "Koksidioasi merupakan penyakit parasiter pada sistem penceranaan babi akibat infeksi protozoa genus Emira.",
                        "imgicon",
                        "Penyebab",
                        "Pencegahan",
                        "Pengobatan"
                ));
        SQLiteHelper
                .getInstance(this)
                .addPenyakit(new Penyakit(
                        "H7",
                        "Penyakit Ascariasis",
                        "",
                        "Ascariasis adalah penyakit parasit yang disebabkan oleh infeksi cacing nematoda" +
                                "dari famili Ascaridae, genus Toxocara.",
                        "imgicon",
                        "Cacing Toxocara vitulorum atau disebut juga Ascaris vitulorum atau Neoascaris" +
                                "vitulorum termasuk kelas nematoda yang memiliki kemampuan melintasi hati," +
                                "paru-paru, dan plasenta. Cacing jantan berukuran panjang sekitar 15-26 cm" +
                                "dengan lebar (pada bagian badan) sekitar 5 mm, sedangkan yang betina lebih" +
                                "panjang, yaitu berukuran 22-30 cm dengan lebar sekitar 6 mm. Telur cacing ini" +
                                "berwarna kuning, berdinding cukup tebal, dengan ukuran telur sekitar 75-95 x" +
                                "60-75 µm.",
                        "Obat berupa anthelmintika, misalnya :" +
                                "a. pyrantel dengan dosis 250 mg per pedet tanpa memperhatikan berat badan\n"+
                                "b. febantel dengan dosis 6 mg/kg berat badan,\n" +
                                "c. levamisole dengan dosis 7,5 mg/kg berat badan,\n" +
                                "d. piperazine citrate dengan dosis 200 mg/kg berat badan, secara oral,\n" +
                                "e. Eprinomectin (Eprinex) dengan dosis 0.5 mg/kg terbukti efektif terhadap T. vitulorum.\n" +
                                "Pengobatan pada induk penderita sangat sulit dilakukan, sebab L3" +
                                "tersembunyi pada otot/organ tubuh. Pemberian anthelmintika yang bersifat" +
                                "sistemik, seperti ivermectin direkomendasikan untuk membunuh larva yang tersembunyi tersebut.",
                        "(1) Pada daerah endemis, peternak dapat memberikan anthelmintika" +
                                "pada pedet yang berumur 10-16 hari untuk membunuh cacing yang" +
                                "belum dewasa.\n" +
                                "(2) Untuk tindakan pencegahan direkomendasikan untuk melakukan" +
                                "pengobatan secara teratur pada pedet dan menjaga kebersihan" +
                                "kandang."
                ));
        SQLiteHelper
                .getInstance(this)
                .addPenyakit(new Penyakit(
                        "H8",
                        "Penyakit Salmonellosis",
                        "",
                        "Salmonellosis adalah penyakit menular yang dapat menyerang hewan maupun manusia. " +
                                "Bakteri penyebab penyakit dapat menimbulkan berbagai macam manifestasi " +
                                "penyakit pada hewan dan demam enterik serta gastroenteritis pada manusia.",
                        "imgicon",
                        "Salmonellosis disebabkan oleh Bakteri Salmonella, ada lebih dari 1800 serotipe" +
                                "Salmonella ditemukan pada hewan dan manusia, termasuk hewan liar, reptilia," +
                                "burung liar dan insekta. Beberapa serotipe tidak mempunyai inang yang spesifik" +
                                "dan gejala yang ditimbulkan tidak khas misalnya Salmonella typhimurium.",
                        " a. Sulfonamida: sulfanilamid terhadap infeksi dengan S.typhi, S.paratyphi" +
                                "dan S.gallinarum, dan lain-lain; sulfaquinoxalin dan sulfamerasin untuk" +
                                "infeksi S.pullorum dan S.gallinarum, sulfagunanidin untuk infeksi" +
                                "S.cholerasuis.\n" +
                                "b. Nitrofurans: nitrofurazone untuk infeksi S.cholerasuis, untuk infeksi" +
                                "S.pullorum dan S.gallinarum.\n" +
                                "c. Antibiotika: streptomycin, neomycin, aureomycin dan terramicyn untuk" +
                                "infeksi bakteri Salmonella pada umumnya.",
                        "Pencegahan salmonellosis dapat dilakukan dengan:\n" +
                                "a. Vaksinasi dengan menggunakan vaksin aktif\n" +
                                "b. Tindakan sanitasi terhadap kandang, peralatan, dan lingkungan" +
                                "peternakan, serta fumigasi penetasan telur ayam.\n" +
                                "c. Pencegahan terhadap pemasukan hewan terinfeksi atau carrier."
                ));
    }

    private void queryGejala(){
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E1", "Nafsu Makan Berkurang"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E2", "Lemas"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E3", "Demam"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E4", "Muntah"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E5", "Diare Kunign"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E6", "Kemerahan Pada Kulit"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E7", "Pendarahan di Bagian Permukaan Kulit"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E8", "Kejang"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E9", "Gatal-Gatal"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E10", "Muncul Bintik Merah"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E11", "Ada Nanah di Kulit"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E12", "Diare Putih"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E13", "Bulu Berdiri"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E14", "Bengkak di Persendian"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E15", "Sulit Berjalan"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E16", "Gemetar"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E17", "Bulu Kusam"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E18", "Pertumbuhan Terhambat"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E19", "Diare Berdarah terus menerus"));
        SQLiteHelper.getInstance(this).addGejala(new Gejala("", "E20", "Batuk"));
    }

    private void queryKeputusan(){
        SQLiteHelper.getInstance(this).addKeputusan(new Keputusan("H1","E1,E2,E3,E4,E5,E6,E9", "0.5"));
        SQLiteHelper.getInstance(this).addKeputusan(new Keputusan("H2","E1,E6,E9,E10,E11","0.6"));
        SQLiteHelper.getInstance(this).addKeputusan(new Keputusan("H3","E1,E2,E12,E13", "0.6"));
        SQLiteHelper.getInstance(this).addKeputusan(new Keputusan("H4","E1,E2,E3,E14,E15,E16", "0.7"));
        SQLiteHelper.getInstance(this).addKeputusan(new Keputusan("H5","E1,E3,E6,E9,E10,E14","0.5"));
        SQLiteHelper.getInstance(this).addKeputusan(new Keputusan("H6","E1,E2,E17,E18,E19","0.4"));
        SQLiteHelper.getInstance(this).addKeputusan(new Keputusan("H7","E5,E13,E18","0.35"));
        SQLiteHelper.getInstance(this).addKeputusan(new Keputusan("H8","E1,E2,E3,E5,E20","0.3"));
    }
}

