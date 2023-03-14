package com.projek.labcheck.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projek.labcheck.entity.Pengguna;
import com.projek.labcheck.entity.Pesanan;
import com.projek.labcheck.entity.PesananLog;
import com.projek.labcheck.repository.PesananLogRepository;

@Service
public class PesananLogService {
    
    @Autowired
    private PesananLogRepository pesananLogRepository;

    public final static int DRAFT = 0;
    public final static int PEMBAYARAN = 10;
    public final static int PENGAMBILAN_SAMPEL = 20;
    public final static int UJI_SAMPEL = 30;
    public final static int SELESAI = 40;
    public final static int DIBATALKAN = 90;

    public void createLog(String username, Pesanan pesanan, int type, String message){
        PesananLog pesananLog = new PesananLog();
        pesananLog.setId(UUID.randomUUID().toString());
        pesananLog.setLog_message(message);
        pesananLog.setPengguna(new Pengguna(username));
        pesananLog.setLog_type(type);
        pesananLog.setPesanan(pesanan);
        pesananLog.setWaktu(new Date());
        pesananLogRepository.save(pesananLog);
    }
}
