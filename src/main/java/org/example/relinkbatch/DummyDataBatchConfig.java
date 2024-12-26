package org.example.relinkbatch;


import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.example.relinkbatch.domain.*;
import org.example.relinkbatch.domain.enums.ItemQuality;
import org.example.relinkbatch.domain.enums.Role;
import org.example.relinkbatch.domain.enums.TradeStatus;
import org.example.relinkbatch.domain.repository.CategoryRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Configuration
public class DummyDataBatchConfig {

    private final Category cateGory = new Category("puma");
    private final CategoryRepository cateGoryRepository;

    public DummyDataBatchConfig(CategoryRepository cateGoryRepository) {
        this.cateGoryRepository = cateGoryRepository;
    }

    @Bean
    public Job generateDummyDataJob(
            JobRepository jobRepository,
            Step generateDummyData,
            Step saveCategory
    ) {
        return new JobBuilder("generateDummyDataJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(saveCategory)
                .next(generateDummyData)
                .build();
    }

    @Bean
    public Step saveCategory(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("saveCategory", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        cateGoryRepository.save(cateGory);
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }

    @Bean
    public Step generateDummyData(JobRepository jobRepository, PlatformTransactionManager transactionManager, EntityManagerFactory entityManagerFactory) {
        return new StepBuilder("generateDummyData", jobRepository)
                .<MainCyclePair, MainCyclePair>chunk(1000, transactionManager)
                .reader(pairItemReader())
                .writer(pairItemWriter(entityManagerFactory))
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<MainCyclePair> pairItemReader() {
        return new ItemReader<MainCyclePair>() {

            private int count = 0;
            private final Faker faker = new Faker();

            @Override
            public MainCyclePair read() {
                if (count >= 500_000) return null;
                count++;

                User ownerUser = User.builder()
                        .name(UUID.randomUUID().toString().substring(0, 15))
                        .email(UUID.randomUUID().toString().substring(0, 20))
                        .password(faker.crypto().sha1())
                        .isDeleted(false)
                        .nickname(UUID.randomUUID().toString().substring(0, 15))
                        .contact(faker.address().zipCode())
                        .address(new Address(Integer.parseInt(faker.number().digit()), faker.address().city(), faker.address().streetAddress()))
                        .role(Role.USER)
                        .build();

                User requesterUser = User.builder()
                        .name(UUID.randomUUID().toString().substring(0, 15))
                        .email(UUID.randomUUID().toString().substring(0, 20))
                        .password(faker.crypto().sha1())
                        .isDeleted(false)
                        .nickname(UUID.randomUUID().toString().substring(0, 15))
                        .contact(faker.address().zipCode())
                        .address(new Address(Integer.parseInt(faker.number().digit()), faker.address().city(), faker.address().streetAddress()))
                        .role(Role.USER)
                        .build();

                Point ownerUserPoint = Point.builder()
                        .amount(0)
                        .user(ownerUser)
                        .build();

                Point requesterUserPoint = Point.builder()
                        .amount(0)
                        .user(requesterUser)
                        .build();

                ExchangeItem ownerExchangeItem = ExchangeItem.builder()
                        .name(faker.commerce().productName())
                        .brand(faker.company().name())
                        .size("100")
                        .deposit(0)
                        .description(faker.name().fullName())
                        .cateGory(cateGory)
                        .itemQuality(ItemQuality.NEW)
                        .user(ownerUser)
                        .tradeStatus(TradeStatus.AVAILABLE)
                        .isDeleted(false)
                        .build();

                ExchangeItem requesterExchangeItem = ExchangeItem.builder()
                        .name(faker.commerce().productName())
                        .brand(faker.company().name())
                        .size("100")
                        .deposit(0)
                        .description(faker.name().firstName())
                        .cateGory(cateGory)
                        .itemQuality(ItemQuality.NEW)
                        .user(requesterUser)
                        .tradeStatus(TradeStatus.AVAILABLE)
                        .isDeleted(false)
                        .build();

                Trade trade = Trade.builder()
                        .requester(requesterUser)
                        .ownerExchangeItem(ownerExchangeItem)
                        .requesterExchangeItme(requesterExchangeItem)
                        .tradeStatus(TradeStatus.AVAILABLE)
                        .hasOwnerReceived(false)
                        .hasRequesterReceived(false)
                        .hasOwnerRequested(false)
                        .hasRequesterRequested(false)
                        .build();

                Review ownerReview = Review.builder()
                        .writer(requesterUser)
                        .exchangeItem(ownerExchangeItem)
                        .star(BigDecimal.valueOf(new Random().nextDouble(5) + 1))
                        .build();

                Review requesterReview = Review.builder()
                        .writer(ownerUser)
                        .exchangeItem(requesterExchangeItem)
                        .star(BigDecimal.valueOf(new Random().nextDouble(5) + 1))
                        .build();

                DonationItem ownerDonationItem = DonationItem.builder()
                        .name(faker.name().username())
                        .description(faker.name().fullName())
                        .user(ownerUser)
                        .cateGory(cateGory)
                        .size("100")
                        .itemQuality(ItemQuality.NEW)
                        .build();

                return new MainCyclePair(
                        requesterUser,
                        ownerUser,
                        trade,
                        requesterExchangeItem,
                        ownerExchangeItem,
                        ownerDonationItem,
                        requesterReview,
                        ownerReview,
                        requesterUserPoint,
                        ownerUserPoint
                );
            }
        };
    }

    @Bean
    public ItemWriter<MainCyclePair> pairItemWriter(EntityManagerFactory emf) {
        return items -> {
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();

            try {
                tx.begin();
                for (MainCyclePair item : items) {
                    em.persist(item.getOwnerUser());
                    em.persist(item.getRequestUser());
                    em.persist(item.getOwnerExchangeItem());
                    em.persist(item.getRequesterExchangeItem());
                    em.persist(item.getOwnerDonationItem());
                    em.persist(item.getTrade());
                    em.persist(item.getOwnerUserPoint());
                    em.persist(item.getRequesterUserPoint());
                    em.persist(item.getOwnerReview());
                    em.persist(item.getRequesterReview());

                    em.flush();
                    em.clear();
                }

                tx.commit();
            } catch (Exception e) {
                tx.rollback();
            } finally {
                em.close();
            }
        };
    }
}
