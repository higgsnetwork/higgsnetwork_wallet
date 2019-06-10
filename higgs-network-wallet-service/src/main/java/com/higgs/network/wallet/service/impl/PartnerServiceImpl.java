package com.higgs.network.wallet.service.impl;

import com.higgs.network.wallet.dao.PartnerInfoDAO;
import com.higgs.network.wallet.domain.PartnerInfo;
import com.higgs.network.wallet.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PartnerService")
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    PartnerInfoDAO partnerInfoDAO;

    @Override
    public List<PartnerInfo> getAllPartnerInfoList(){
        return partnerInfoDAO.getAllPartnerInfoList();
    }

    @Override
    public PartnerInfo getPartnerInfoByID(Integer id){
        if(id>0){
            return partnerInfoDAO.getPartnerInfoByID(id);
        }else{
            return null;
        }
    }

    @Override
    public PartnerInfo getPartnerInfoByHash(String hash){
        if(!hash.equals("")){
            return partnerInfoDAO.getPartnerInfoByHash(hash);
        }else{
            return null;
        }
    }

    @Override
    public List<PartnerInfo> getAllPartnerInfoListByIsOpen(){
            return partnerInfoDAO.getAllPartnerInfoListByIsOpen();
    }
}
