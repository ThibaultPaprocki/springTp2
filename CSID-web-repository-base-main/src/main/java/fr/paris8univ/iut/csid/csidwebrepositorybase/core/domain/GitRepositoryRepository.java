package fr.paris8univ.iut.csid.csidwebrepositorybase.core.domain;
import fr.paris8univ.iut.csid.csidwebrepositorybase.core.dao.GitRepositoryDTO;
import fr.paris8univ.iut.csid.csidwebrepositorybase.core.dao.GitRepositoryDao;
import fr.paris8univ.iut.csid.csidwebrepositorybase.core.dao.GitRepositoryEntity;
import fr.paris8univ.iut.csid.csidwebrepositorybase.core.dao.GithubRepositoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GitRepositoryRepository {

    final private GitRepositoryDao repDAO;
    final private GithubRepositoryDao githubRepDAO;

    @Autowired
    public GitRepositoryRepository(GitRepositoryDao repDAO, GithubRepositoryDao githubRepDAO) {
        this.repDAO=repDAO;
        this.githubRepDAO=githubRepDAO;
    }

    public List<GitRepository> getRepositories() {
        List<GitRepositoryEntity> repositories = repDAO.findAll();
        return repositories.stream().map(x -> new GitRepository(x.getName(), x.getOwner(), x.getOpen_issues(), x.getForks())).collect(Collectors.toList());
    }

    public Optional<GitRepository> getOneRepository(String name) throws URISyntaxException {
        GitRepositoryEntity gitE = repDAO.findById(name).get();
        GitRepository gitRepo = new GitRepository(gitE.getName(),gitE.getOwner(),gitE.getForks(),gitE.getOpen_issues());
        //TODo Créer variable de temps qui calcule à chaque update.
        //if(tempsUpdate>5){
           GitRepositoryDTO gitDTO = this.githubRepDAO.getGithubLink(gitRepo.getName(),gitRepo.getOwner());
           gitRepo.setForks(gitDTO.getForks());
           gitRepo.setOpen_issues(gitDTO.getOpen_issues());
           this.partialUpdate(name,gitRepo);
        //}
        return Optional.of(gitRepo);
    }

    public boolean add(GitRepository gitRepo) {
        this.repDAO.save(new GitRepositoryEntity(gitRepo.getName(), gitRepo.getOwner(), gitRepo.getForks(),  gitRepo.getOpen_issues()));
        return true;
    }

    public boolean delete(String nomGitRepo) {
        this.repDAO.deleteById(nomGitRepo);
        return true;
    }

    public boolean update(String name, GitRepository gitRepo) {
        if (repDAO.findById(name).isPresent()) {
            this.delete(name);
            this.add(gitRepo);
            return true;
        }
        return false;
    }

    public boolean partialUpdate(String name, GitRepository gitRepo) {
        GitRepository newRepo = new GitRepository("placeholder", "placeholder", 270, 270);
        GitRepositoryEntity originalRepoEntity = repDAO.getOne(name);
        newRepo.setName(originalRepoEntity.getName());
        newRepo.setOwner(originalRepoEntity.getOwner());
        newRepo.setOpen_issues(originalRepoEntity.getOpen_issues());
        newRepo.setForks(originalRepoEntity.getForks());

        if (gitRepo.getOwner() != null)
            newRepo.setOwner(gitRepo.getOwner());
        if (gitRepo.getOpen_issues() != null)
            newRepo.setOpen_issues(gitRepo.getOpen_issues());
        if (gitRepo.getForks() != null)
            newRepo.setForks(gitRepo.getForks());
        this.update(name, newRepo);
        return true;
    }
}