package com.ostapdev.izosbotvk.service;

import com.ostapdev.izosbotvk.model.peer.BotState;
import com.ostapdev.izosbotvk.model.peer.PeerConfig;
import com.ostapdev.izosbotvk.repo.PeerConfigRepo;
import com.ostapdev.izosbotvk.sender.VkMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PeerConfigService {
    private final PeerConfigRepo peerConfigRepo;
    private final VkMessageSender messageSender;

    @Autowired
    public PeerConfigService(PeerConfigRepo peerConfigRepo, VkMessageSender messageSender) {
        this.peerConfigRepo = peerConfigRepo;
        this.messageSender = messageSender;
    }

    public PeerConfig getCurrentPeerConfig(Integer peerId){
        return this.peerConfigRepo.getAllByPeerId(peerId);
    }

    public void getPeerGroupNumber(Integer peerId){
        messageSender.generateMessage(peerId,"Группа беседы " + getCurrentPeerConfig(peerId).getGroupNumber());
    }

    public void updateCurrentGroup(Integer peerId,String group){
        PeerConfig peerConfig = getCurrentPeerConfig(peerId);
        peerConfig.setGroupNumber(group);
        peerConfigRepo.save(peerConfig);
        messageSender.generateMessage(peerId,"Группа беседы изменена на " + group);
    }

    public void addNewPeerConfig(Integer peerId,String group,BotState botState, boolean isMailing){
        peerConfigRepo.save(new PeerConfig(peerId,group, botState,isMailing));
    }

    public BotState getBotState(Integer peerId){
        return getCurrentPeerConfig(peerId).getBotState();
    }

    public void setBotState(Integer peerId,BotState botState){
        PeerConfig peerConfig = getCurrentPeerConfig(peerId);
        peerConfig.setBotState(botState);
        peerConfigRepo.save(peerConfig);
    }

    public List<Integer> getAllPeerIds(){
        List<Integer> peers = new ArrayList<>();
        peerConfigRepo.findAll().forEach(peerConfig -> peers.add(peerConfig.getPeerId()));
        return peers;
    }

    public List<PeerConfig> getAllPeers(){
        return peerConfigRepo.findAll();
    }

    public void setIsMailing(Integer peerId,boolean isMailing){
        PeerConfig peerConfig = getCurrentPeerConfig(peerId);
        peerConfig.setMailing(isMailing);
        peerConfigRepo.save(peerConfig);
        messageSender.generateMessage(peerId
                ,"Ежедневная рассылка расписания " + (isMailing ? "включена" : "отключена"));
    }

    public boolean getIsMailing(Integer peerId){
        return getCurrentPeerConfig(peerId).isMailing();
    }
}
