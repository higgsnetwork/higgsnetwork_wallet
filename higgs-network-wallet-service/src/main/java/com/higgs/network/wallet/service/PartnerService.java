package com.higgs.network.wallet.service;

import com.higgs.network.wallet.domain.PartnerInfo;

import java.util.List;

public interface PartnerService {

    List<PartnerInfo> getAllPartnerInfoList();

    PartnerInfo getPartnerInfoByID(Integer id);

    PartnerInfo getPartnerInfoByHash(String hash);

    List<PartnerInfo> getAllPartnerInfoListByIsOpen();
}
