data class MataKuliah(
    val id: Int,
    var nama: String,
)

data class Tugas(
    var id: Int,
    var IDMatkul: Int,
    var judul: String,
    var deskripsi: String,
    var dueDate: String,
    var status: Status = Status.PENDING
)

enum class Status {
    PENDING, COMPLETED
}

//fun tambahMatkul(), fun tambahTugas(), fun editTugas(), fun hapusTugas(), fun menandaiTugasSelesai(),
// fun listTugas(), fun listMatkul berada di dalam class ManajemenTugas
class ManajemenTugas {

    private val matkulMT = mutableListOf<MataKuliah>()
    private val tugasMT = mutableListOf<Tugas>()
    private var idTugasDimulai = 1 //id dihitung dari 1
    private var idMatkulDimulai = 1


    fun tambahMatkul(nama: String) {
        val matkul = MataKuliah(idMatkulDimulai++, nama)
        matkulMT.add(matkul)
        println("Mata Kuliah sudah ditambahkan: $matkul")
    }

    fun tambahTugas(idMK: Int, judul: String, description: String, dueDate: String) {
        if (matkulMT.any { it.id == idMK })
        {
            val tugas = Tugas(idTugasDimulai++, idMK, judul, description, dueDate)
            tugasMT.add(tugas)
            println("Tugas ditambahkan: $tugas")
        } else {
            println("Matakuliah tidak tersedia")
        }
    }

    fun editTugas(namaMK: String, taskId: Int, title: String, description: String, dueDate: String) {
        val matkul1 = matkulMT.find { it.nama.equals(namaMK, ignoreCase = true)
        }
        if (matkul1 != null)
        {
            val task = tugasMT.find { it.id == taskId && it.IDMatkul == matkul1.id }

            task?.apply {
                this.judul = title
                this.deskripsi = description
                this.dueDate = dueDate
            }
            println("Tugas yang sudah diedit: $task")
        } else {
            println("Mata Kuliah tidak tersedia")
        }
    }

    fun hapusTugas(namaMK: String, idTugas: Int) {
        val matkul2 = matkulMT.find { it.nama.equals(namaMK, ignoreCase = true)
        }
        if (matkul2 != null)
        {
            tugasMT.removeAll { it.id == idTugas && it.IDMatkul == matkul2.id }
            println("Task deleted with id: $idTugas")
        } else {
            println("Mata Kuliah tidak tersedia")
        }
    }


    fun menandaiTugasSelesai(namaMK: String, idTugas: Int) {
        val matkul3 = matkulMT.find { it.nama.equals(namaMK, ignoreCase = true)
        }
        if (matkul3 != null)
        {
            val task = tugasMT.find { it.id == idTugas && it.IDMatkul == matkul3.id }
            task?.status = Status.COMPLETED
            println("Tugas yang sudah selesai: $task")
        } else {
            println("Mata kuliah tidak tersedia")
        }
    }

    fun listTugas() {
        tugasMT.forEach { println(it) }
    }

    fun listMatkul() {
        matkulMT.forEach { println(it) }
    }
}

fun tampilanMenu() {
    println("""
        ==== MENU PILIHAN ====
        1. Tambah Mata Kuliah
        2. Tambah Tugas
        3. Edit Tugas
        4. Hapus Tugas
        5. Tandai Tugas Selesai
        6. Lihat Daftar Tugas
        7. Lihat Daftar Mata Kuliah
        8. Keluar
    """)
}

fun main() {
    val manageTugas = ManajemenTugas()

    while (true) {
        tampilanMenu()
        print("Pilih opsi: ")
        when (readLine()?.toIntOrNull())
        {
            1 -> {
                print("Nama Mata Kuliah: ")
                val name = readLine() ?: ""
                manageTugas.tambahMatkul(name)
            }
            2 -> {
                print("ID Mata Kuliah: ")
                val idMatkul = readLine()?.toIntOrNull()
                if (idMatkul != null) {
                    print("Judul: ")
                    val title = readLine() ?: ""
                    print("Deskripsi: ")
                    val description = readLine() ?: ""

                    print("Tanggal Tenggat (YYYY-MM-DD): ")
                    val dueDate = readLine() ?: ""
                    manageTugas.tambahTugas(idMatkul, title, description,  dueDate)
                } else {
                    println("ID Mata Kuliah tidak valid.")
                }
            }
            3 -> {
                print("Nama Mata Kuliah: ")
                val namaMatkul = readLine() ?: ""
                print("ID Tugas yang akan diedit: ")
                val taskId = readLine()?.toIntOrNull()
                if (taskId != null) {
                    print("Judul: ")
                    val title = readLine() ?: ""
                    print("Deskripsi: ")
                    val description = readLine() ?: ""

                    print("Tanggal Tenggat (YYYY-MM-DD): ")
                    val dueDate = readLine() ?: ""
                    manageTugas.editTugas(namaMatkul, taskId, title, description, dueDate)
                } else {
                    println("ID Tugas tidak valid.")
                }
            }
            4 -> {
                print("Nama Mata Kuliah: ")
                val namaMatkul = readLine() ?: ""
                print("ID Tugas yang akan dihapus: ")
                val taskId = readLine()?.toIntOrNull()
                if (taskId != null) {
                    manageTugas.hapusTugas (namaMatkul, taskId)
                } else {
                    println("ID Tugas tidak valid.")
                }
            }
            5 -> {
                print("Nama Mata Kuliah: ")
                val namaMatkul = readLine() ?: ""
                print("ID Tugas yang akan ditandai selesai: ")
                val taskId = readLine()?.toIntOrNull()
                if (taskId != null) {
                    manageTugas.menandaiTugasSelesai (namaMatkul, taskId)
                } else {
                    println("ID Tugas tidak valid.")
                }
            }
            6 -> manageTugas.listTugas()
            7 -> manageTugas.listMatkul()
            8 -> break
            else -> println("Opsi tidak valid.")
        }
    }
}