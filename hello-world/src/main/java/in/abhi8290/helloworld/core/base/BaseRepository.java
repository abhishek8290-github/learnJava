    package in.abhi8290.helloworld.core.base;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.repository.NoRepositoryBean;

    @NoRepositoryBean
    public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
        // Add global reusable methods here (if needed in future)
    }
