package com.component.testing.demo.service;

import com.component.testing.demo.dto.ReleaseDTO;
import com.component.testing.demo.dao.ISoftwareReleaseDAO;
import com.component.testing.demo.entity.SoftwareRelease;
import com.component.testing.demo.helper.GitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SoftwareReleaseService implements ISoftwareReleaseService {

    @Autowired
    private ISoftwareReleaseDAO softwareReleaseDAO;

    @Autowired
    private GitClient gitClient;

    @Override
    public Integer addRelease(SoftwareRelease softwareRelease) {
        softwareReleaseDAO.addRelease(softwareRelease);
        return softwareRelease.getId();
    }

    @Override
    public void addApplication(Integer appId, Integer releaseId) {
        softwareReleaseDAO.addApplication(appId, releaseId);
    }

    @Override
    public ReleaseDTO getReleaseById(Integer releaseId) {
        SoftwareRelease softwareRelease = softwareReleaseDAO.getReleaseById(releaseId);
        if (softwareRelease == null) {
            return null;
        }
        ReleaseDTO releaseDTO = new ReleaseDTO(softwareRelease);
        List<String> gitTags = gitClient.getReleaseTags(softwareRelease.getReleaseDate(), softwareRelease.getApplications());
        releaseDTO.setGitTags(gitTags);
        return releaseDTO;
    }
}
