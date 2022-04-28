package com.ncedu.nc_edu.services.impl;

import com.ncedu.nc_edu.dto.resources.RecipeSearchCriteria;
import com.ncedu.nc_edu.models.Ingredient;
import com.ncedu.nc_edu.models.IngredientsRecipes;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeSearchSpecification implements Specification<Recipe> {
    private RecipeSearchCriteria criteria;
    private Set<Tag> includeTags, excludeTags;
    private Set<Ingredient> includeIngredients, excludeIngredients;

    public RecipeSearchSpecification(
            RecipeSearchCriteria criteria,
            Set<Tag> includeTags,
            Set<Tag> excludeTags,
            Set<Ingredient> includeIngredients,
            Set<Ingredient> excludeIngredients
    ) {
        this.criteria = criteria;
        this.includeTags = includeTags;
        this.excludeTags = excludeTags;
        this.includeIngredients = includeIngredients;
        this.excludeIngredients = excludeIngredients;
    }

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        query.orderBy(criteriaBuilder.asc(root.get("id")));
        return criteriaBuilder.and(fill(root, query, criteriaBuilder).toPredicate(root, query, criteriaBuilder));
    }

    private Specification<Recipe> fill(Root<Recipe> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
        Specification<Recipe> spec = (root, query, criteriaBuilder) -> null;

        if (criteria.getName() != null) {
            spec = spec.and(containsName(criteria.getName()));
        }

        if (criteria.getCaloriesMin() != null) {
            spec = spec.and(caloriesGreaterThanOrEqualTo(criteria.getCaloriesMin()));
        }

        if (criteria.getCaloriesMax() != null) {
            spec = spec.and(caloriesLessThanOrEqualTo(criteria.getCaloriesMax()));
        }

        if (criteria.getFatsMin() != null) {
            spec = spec.and(fatsGreaterThanOrEqualTo(criteria.getFatsMin()));
        }

        if (criteria.getFatsMax() != null) {
            spec = spec.and(fatsLessThanOrEqualTo(criteria.getFatsMax()));
        }

        if (criteria.getCarbohydratesMin() != null) {
            spec = spec.and(carbohydratesGreaterThanOrEqualTo(criteria.getCarbohydratesMin()));
        }

        if (criteria.getCarbohydratesMax() != null) {
            spec = spec.and(carbohydratesLessThanOrEqualTo(criteria.getCarbohydratesMax()));
        }

        if (criteria.getProteinsMin() != null) {
            spec = spec.and(proteinsGreaterThanOrEqualTo(criteria.getProteinsMin()));
        }

        if (criteria.getProteinsMax() != null) {
            spec = spec.and(proteinsLessThanOrEqualTo(criteria.getProteinsMax()));
        }

        if (criteria.getRatingMin() != null) {
            spec = spec.and(ratingGreaterThanOrEqualTo(criteria.getRatingMin()));
        }

        if (criteria.getRatingMax() != null) {
            spec = spec.and(ratingLessThanOrEqualTo(criteria.getRatingMax()));
        }

        if (criteria.getCookingMethods() != null) {
            if (criteria.getCookingMethods().size() != 0) {
                spec = spec.and(inCookingMethods(
                        criteria.getCookingMethods().stream().map(Recipe.CookingMethod::valueOf).collect(Collectors.toSet()))
                );
            }
        }

        if (criteria.getCookingTimeMin() != null) {
            spec = spec.and(cookingTimeGreaterThanOrEqualTo(criteria.getCookingTimeMin()));
        }

        if (criteria.getCookingTimeMax() != null) {
            spec = spec.and(cookingTimeLessThanOrEqualTo(criteria.getCookingTimeMax()));
        }

        if (criteria.getPriceMin() != null) {
            spec = spec.and(priceGreaterThanOrEqualTo(criteria.getPriceMin()));
        }

        if (criteria.getPriceMax() != null) {
            spec = spec.and(priceLessThanOrEqualTo(criteria.getPriceMax()));
        }

        if (criteria.getCuisines() != null) {
            if (criteria.getCuisines().size() != 0) {
                spec = spec.and(inCuisines(criteria.getCuisines()
                        .stream().map(Recipe.Cuisine::valueOf).collect(Collectors.toSet()))
                );
            }
        }

        if (this.includeTags.size() > 0) {
            spec = spec.and(containTags(this.includeTags));
        }

        if (this.excludeTags.size() > 0) {
            spec = spec.and(notContainTags(this.excludeTags));
        }

        if (this.includeIngredients.size() > 0) {
            spec = spec.and(containIngredients(this.includeIngredients));
        }

        if (this.excludeIngredients.size() > 0) {
            spec = spec.and(notContainIngredients(this.excludeIngredients));
        }

        return spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("visible"), true));
    }

    private Specification<Recipe> caloriesGreaterThanOrEqualTo(Integer value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("calories"), value);
    }

    private Specification<Recipe> caloriesLessThanOrEqualTo(Integer value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("calories"), value);
    }

    private Specification<Recipe> fatsLessThanOrEqualTo(Float value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("fats"), value);
    }

    private Specification<Recipe> fatsGreaterThanOrEqualTo(Float value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("fats"), value);
    }

    private Specification<Recipe> carbohydratesLessThanOrEqualTo(Float value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("carbohydrates"), value);
    }

    private Specification<Recipe> carbohydratesGreaterThanOrEqualTo(Float value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("carbohydrates"), value);
    }

    private Specification<Recipe> proteinsLessThanOrEqualTo(Float value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("proteins"), value);
    }

    private Specification<Recipe> proteinsGreaterThanOrEqualTo(Float value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("proteins"), value);
    }

    private Specification<Recipe> ratingGreaterThanOrEqualTo(Float value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), value);
    }

    private Specification<Recipe> ratingLessThanOrEqualTo(Float value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("rating"), value);
    }

    private Specification<Recipe> containsName(String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + value + "%");
    }

    private Specification<Recipe> inCuisines(Set<Recipe.Cuisine> cuisines) {
        return (root, query, criteriaBuilder) ->
                root.get("cuisine").in(cuisines);
    }

    private Specification<Recipe> cookingTimeGreaterThanOrEqualTo(Integer value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("cookingTime"), value);
    }

    private Specification<Recipe> cookingTimeLessThanOrEqualTo(Integer value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("cookingTime"), value);
    }

    private Specification<Recipe> priceGreaterThanOrEqualTo(Integer value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
    }

    private Specification<Recipe> priceLessThanOrEqualTo(Integer value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
    }

    private Specification<Recipe> inCookingMethods(Set<Recipe.CookingMethod> methods) {
        return (root, query, criteriaBuilder) -> {
            SetJoin<Recipe, Recipe.CookingMethod> recipeCookingMethodSetJoin =
                    root.joinSet("cookingMethods", JoinType.INNER);

            return recipeCookingMethodSetJoin.in(methods);
        };
    }

    private Specification<Recipe> containTags(Set<Tag> tags) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Recipe> sq = query.subquery(Recipe.class);
            Root<Recipe> recipe = sq.from(Recipe.class);
            SetJoin<Recipe, Tag> recipeTagSetJoin = recipe.joinSet("tags", JoinType.INNER);

            return root.in(
                    sq.select(recipe).groupBy(recipe.get("id"))
                            .having(criteriaBuilder.equal(
                                    criteriaBuilder.count(recipe), tags.size()
                                    )
                            ).where(recipeTagSetJoin.in(tags))
            );
        };
    }

    private Specification<Recipe> notContainTags(Set<Tag> tags) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Recipe> sq = query.subquery(Recipe.class);
            Root<Recipe> recipe = sq.from(Recipe.class);
            SetJoin<Recipe, Tag> recipeTagSetJoin = recipe.joinSet("tags", JoinType.INNER);

            return criteriaBuilder.not(root.in(
                    sq.select(recipe).groupBy(recipe.get("id"))
                            .having(criteriaBuilder.equal(
                                    criteriaBuilder.count(recipe), tags.size()
                                    )
                            ).where(recipeTagSetJoin.in(tags))
            ));
        };
    }

    private Specification<Recipe> containIngredients(Set<Ingredient> ingredients) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Recipe> sq = query.subquery(Recipe.class);
            Root<Recipe> recipe = sq.from(Recipe.class);
            SetJoin<Recipe, IngredientsRecipes> ingredientsRecipes
                    = recipe.joinSet("ingredientsRecipes", JoinType.INNER);
            Join<IngredientsRecipes, Ingredient> ingredientsJoin
                    = ingredientsRecipes.join("ingredient", JoinType.INNER);

            return root.in(
                    sq.select(recipe).groupBy(recipe.get("id"))
                            .having(criteriaBuilder.equal(
                                    criteriaBuilder.count(recipe), ingredients.size()
                                    )
                            ).where(ingredientsJoin.in(ingredients))
            );
        };
    }

    private Specification<Recipe> notContainIngredients(Set<Ingredient> ingredients) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Recipe> sq = query.subquery(Recipe.class);
            Root<Recipe> recipe = sq.from(Recipe.class);
            SetJoin<Recipe, IngredientsRecipes> ingredientsRecipes
                    = recipe.joinSet("ingredientsRecipes", JoinType.INNER);
            Join<IngredientsRecipes, Ingredient> ingredientsJoin
                    = ingredientsRecipes.join("ingredient", JoinType.INNER);

            return criteriaBuilder.not(root.in(
                    sq.select(recipe).groupBy(recipe.get("id"))
                            .having(criteriaBuilder.equal(
                                    criteriaBuilder.count(recipe), ingredients.size()
                                    )
                            ).where(ingredientsJoin.in(ingredients))
            ));
        };
    }
}
