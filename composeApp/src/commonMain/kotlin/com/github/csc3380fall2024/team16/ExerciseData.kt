package com.github.csc3380fall2024.team16

data class Exercise(
    val name: String,
    val target: String,
    val equipment: String,
    val description: String
)

object ExerciseRepository {
    fun getBodybuildingExercises(): List<Exercise> = listOf(
        Exercise(
            "Dumbbell Press", "Chest", "Dumbbells, Machine",
            "Compound exercise that involves pushing weight upwards, away from the body. We use dumbbells for muscle isolation."
        ),
        Exercise(
            "Incline Dumbbell Press", "Chest", "Dumbbells, Machine",
            "Compound exercise just like the dumbbell press, but laying at an incline angle to focus on the upper pectorials."
        ),
        Exercise(
            "Decline Dumbbell Press", "Chest", "Dumbbells, Machine",
            "Compound exercise just like the dumbbell press, but laying at a decline angle. This helps focus more on the lower pectorials"
        ),
        Exercise(
            "Chest Flys", "Chest", "Machine, Dumbbells, Cables",
            "From a wide arc, pull the weight together at the center of your chest. This isolates and squeezes the pectorials."
        ),
        Exercise(
            "Shoulder Press", "Arms", "Dumbbells, Machine",
            "Lift the dumbbells from shoulder height to above the head. Fully extend arms but don't lock the elbows."
        ),
        Exercise(
            "Lateral Raises", "Arms", "Dumbbells",
            "Lift arms to the sides at shoulder height. Keep elbows bent at a slight angle."
        ),
        Exercise(
            "Front Raises", "Arms", "Dumbbells",
            "Lift arms to the front at shoulder height. Keep elbows bent at a slight angle."
        ),
        Exercise(
            "Rear Delt Flys", "Arms", "Dumbbells",
            "Bend to the waist, then lift dumbbells to the side. This angle focuses on the rear deltoids."
        ),
        Exercise(
            "Shrugs", "Back", "Dumbbells",
            "Holding dumbbells to your side and standing upright, pull the weight with a 'shrugging motion', Don't bend elbows."
        ),
        Exercise(
            "Tricep Pushdown", "Arms", "Cables",
            "Bend at a slight angle, pull the rope/bar down by extending your elbows. Try to keep your arms still."
        ),
        Exercise(
            "Skullcrushers", "Arms", "Dumbbells, Barbell",
            "Laying down on a bench, lower the weight towards your forehead getting a full stretch of the triceps, then extend."
        ),
        Exercise(
            "Close Grip Press", "Arms", "Dumbbells",
            "Have the close together touching each other. Press the weight away from your body. This grip focuses on the triceps more."
        ),
        Exercise(
            "Dips", "Chest", "Parallel Bars, Bench",
            "Lower your body by bending the elbows, then press up using your triceps. Lower Chest is in play a bit."
        ),
        Exercise(
            "Lat Pulldown", "Back", "Machine, Cables",
            "Sitting down on the machine, pull the weight towards your chest. Focus on squeezing the lats."
        ),
        Exercise(
            "Cable Row", "Back", "Machine, Cables",
            "Sitting upright, pull the weight towards your body. Engage the entire back."
        ),
        Exercise(
            "T-Bar Row", "Back", "T-Bar Machine",
            "Using this machine, pull the weight towards your body and squeeze the back."
        ),
        Exercise(
            "Face Pull", "Back", "Cables",
            "Set the ropes at a high angle and take a few steps back. Pull the rope towards your face."
        ),
        Exercise(
            "Chest Supported Row", "Back", "Dumbbells",
            "Lay chest down on an inclined bench, then pull dumbbells while squeezing back. This helps isolation, and reduces back strain."
        ),
        Exercise(
            "Hammer Curls", "Arms", "Dumbbells",
            "With palms facing towards the body, curl the dumbbells up. Focuses on brachialis."
        ),
        Exercise(
            "Incline Dumbbell Curl", "Arms", "Dumbbells",
            "Laying on an incline bench, curl the dumbbells upwards. Have palms facing upwards. The incline allows more range of motion."
        ),
        Exercise(
            "Preacher Curl", "Arms", "Preacher Machine",
            "Having arms rested on the pad, curl the weight upwards. This machine minimizes any other movement, and focuses on the short head."
        ),
        Exercise(
            "Leg Press", "Legs", "Machine",
            "Push the platform with your legs away from you. Keep butt on the seat. More supported and safer than squats."
        ),
        Exercise(
            "Romanian Deadlift", "Legs", "Dumbbells, Barbell",
            "Hold the weight in hands. Hinge at the hips, with a slight bend in the knee and lower the weight. Stand back up and repeat."
        ),
        Exercise(
            "Bulgarian Split Squat", "Legs", "Dumbbells, Bench",
            "Have one leg elevated upon the bench. Squat down with the other, isolating the muscles."
        ),
        Exercise(
            "Calf Raises", "Legs", "Dumbbells, Barbell, Machine",
            "Rise onto your toes and hold for about a second. Emphasize the calves and balance yourself."
        ),
        Exercise(
            "Hip Thrust", "Legs", "Barbell, Machine",
            "Using a bench for support, or if on machine, lift your hips slowly, engaging the glutes."
        ),
        Exercise(
            "Leg Extension", "Legs", "Machine",
            "Extend your legs from the seated position. Focus on the quads, try to slow down on descension."
        ),
        Exercise(
            "Hamstring Curl", "Legs", "Machine",
            "In seated position, curl your legs towards your glutes. Focus on the hamstrings, try to slow down as legs ascend."
        ),
        Exercise(
            "Crunch", "Abs", "Bodyweight",
            "Lie on your back with your knees bent and feet flat on the ground. Lift your shoulders off the ground, squeezing your abs, and return to the starting position."
        ),
        
        Exercise(
            "Plank", "Abs", "Bodyweight",
            "Get into a push-up position but rest on your forearms instead of your hands. Keep your body in a straight line from head to heels, engaging your core."
        ),
        Exercise(
            "Hanging Leg Raise", "Abs", "Bar",
            "Hang from a pull-up bar with your legs straight. Lift your legs to a 90-degree angle, keeping them straight, and lower them back slowly."
        ),
        Exercise(
            "Russian Twist", "Abs", "Bodyweight",
            "Sit on the floor with your knees bent and feet slightly off the ground. Twist your torso from side to side while keeping your core engaged."
        ),
        Exercise(
            "Bicycle Crunch", "Abs", "Bodyweight",
            "Lie on your back with your hands behind your head. Bring your elbow towards the opposite knee while straightening the other leg, alternating sides in a pedaling motion."
        ),
        Exercise(
            "Ab Wheel Rollout", "Abs", "Equipment",
            "Kneel on the floor and hold the ab wheel with both hands. Slowly roll forward, extending your body, then roll back to the starting position."
        ),
        Exercise(
            "Mountain Climbers", "Abs", "Bodyweight",
            "Start in a push-up position. Bring one knee towards your chest, then switch legs in a running motion while keeping your core tight."
        ),
        Exercise(
            "Side Plank", "Abs", "Bodyweight",
            "Lie on your side with your legs straight. Prop yourself up on one forearm, lifting your hips off the ground, keeping your body in a straight line."
        )
    )
    
    fun getPowerliftingExercises(): List<Exercise> = listOf(
        Exercise(
            "Bench Press", "Bench", "Barbell",
            "Compound exercise that focuses on building strength in the upper body by using force to push away from the body."
        ),
        Exercise(
            "Pause Bench Press", "Bench", "Barbell",
            "Compound exercise like the bench press, with a slight pause on the way up the weight to really engage the muscles."
        ),
        Exercise(
            "Deadlift", "Deadlift", "Barbell",
            "Compound exercise where you lift a loaded barbell from ground level to hip, keeping back straight and driving from the legs."
        ),
        Exercise(
            "Pause Deadlift", "Deadlift", "Barbell",
            "Compoud exercise like the deadlift, with a slight pause to really engage the muscles on the way up."
        ),
        Exercise(
            "Squat", "Squat", "Barbell",
            "With the barbell on your upper back, descend until knee level and drive back up with the legs."
        ),
        Exercise(
            "Pause Squat", "Squat", "Barbell",
            "Compound exercise like the squat, with a slight pause at knee level before you drive back up."
        ),
        Exercise(
            "Military Press", "Bench", "Barbell",
            "Standing up, lift barbell from shoulder level to above head. Keep body balanced and core tight."
        ),
        Exercise(
            "Barbell Rows", "Deadlift", "Barbell",
            "Bending over, keep back straight and pull the weight towards chest. Squeeze the back muscles."
        ),
        Exercise(
            "Leg Press", "Squat", "Machine",
            "Push the platform with your legs away from you. Keep butt on the seat. More supported and safer than squats."
        ),
        Exercise(
            "Leg Press", "Squat", "Machine",
            "Push the platform with your legs away from you. Keep butt on the seat. More supported and safer than squats."
        ),
        Exercise(
            "Romanian Deadlift", "Squat", "Dumbbells, Barbell",
            "Hold the weight in hands. Hinge at the hips, with a slight bend in the knee and lower the weight. Stand back up and repeat."
        ),
        Exercise(
            "Bulgarian Split Squat", "Squat", "Dumbbells, Bench",
            "Have one leg elevated upon the bench. Squat down with the other, isolating the muscles."
        ),
        Exercise(
            "Calf Raises", "Squat", "Dumbbells, Barbell, Machine",
            "Rise onto your toes and hold for about a second. Emphasize the calves and balance yourself."
        ),
        Exercise(
            "Hip Thrust", "Squat", "Barbell, Machine",
            "Using a bench for support, or if on machine, lift your hips slowly, engaging the glutes."
        ),
        Exercise(
            "Leg Extension", "Squat", "Machine",
            "Extend your legs from the seated position. Focus on the quads, try to slow down on descension."
        ),
        Exercise(
            "Hamstring Curl", "Squat", "Machine",
            "In seated position, curl your legs towards your glutes. Focus on the hamstrings, try to slow down as legs ascend."
        )
    )
    
    fun getAthleticExercises(): List<Exercise> = listOf(
        Exercise(
            "Box Jumps", "Vertical", "Plyo Boxes",
            "Stand with feet shoulder width apart, jump onto the box and back down. As soon as you land back down, explode up again."
        ),
        Exercise(
            "Depth Jumps", "Vertical", "Plyo Boxes",
            "Standing on the box, step down and as soon as you make contact with the ground explode up."
        ),
        Exercise(
            "Split Squat Jumps", "Vertical", "None",
            "Starting in a lunge position, squat down and explode up, switching legs mid air and landing in a lunge position opposite from start"
        ),
        Exercise(
            "Hill Sprints", "Speed", "Hill, Slope",
            "Put yourself in take off position with one leg behind the other, then explode up the incline as fast as you can."
        ),
        Exercise(
            "Broad Jumps", "Vertical", "None",
            "Forward leap, emphasizing on distance gained."
        ),
        Exercise(
            "Approach Jumps", "Vertical", "Plyo Boxes",
            "Start with a 2-3 step approach based on your preference, and jump as high as possible landing on the box"
        ),
        Exercise(
            "Sled Pushes", "Endurance", "Sled Bar",
            "Lean slightly forward and push the sled, do not break form and keep back straight."
        ),
        Exercise(
            "Sled Pulls", "Endurance", "Sled Bar",
            "Lean slightly backward and pull the sled, keep back straight and do not break form."
        ),
        Exercise(
            "Squat Jumps", "Vertical", "None",
            "Begin with a bodyweight squat, getting the hips to the knees, then explode up."
        )
    )
    
    fun getWeightLossExercises(): List<Exercise> = listOf(
        Exercise(
            "High Knees", "HIIT", "None",
            "Start jogging in place, but try to raise your knees as high as possible with each step."
        ),
        Exercise(
            "Burpees", "HIIT", "None",
            "Start with a squat jump, then go straight down into a push up and repeat."
        ),
        Exercise(
            "Incline Treadmill", "Cardio", "Treadmill Machine",
            "Set incline to about 12-15%, speed on 2-3mph and continue for around 15 minutes. Walking on an incline works the core and leg muscles more."
        ),
        Exercise(
            "Mountain Climbers", "HIIT", "None",
            "Get down into a plank position, palms flat on the floor. Bring one knee to the chest, then switch to the other and repeat rapidly."
        ),
        Exercise(
            "Plank Jacks", "HIIT", "None",
            "Get down into a plank position, palms flat on the floor. Jump legs in and out rapidly."
        ),
        Exercise(
            "Sprints", "HIIT", "None",
            "Run as fast as you can for 20 seconds, rest for 20, and do it again."
        ),
        Exercise(
            "Stairmaster", "Cardio", "Stairmaster Machine",
            "Select level of intensity wanted. Slow leans towards on muscle growth while fast leans towards calories burned"
        ),
        Exercise(
            "Crunches", "HIIT", "None",
            "Lying down with hands behind your head and heels to your butt, lift your core up towards your knees."
        ),
    )
}
